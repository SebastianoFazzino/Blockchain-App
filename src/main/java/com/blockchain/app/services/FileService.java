package com.blockchain.app.services;

import com.blockchain.app.models.dtos.TransferRequest;
import com.blockchain.app.models.enums.Currency;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final TransferRequestService transferRequestService;

    private final String[] FILE_HEADER = {
            "amount",
            "currency",
            "recipient_address"
    };

    private final String[] SAMPLE_DATA = {
            "0.0001",
            "BTC",
            "3DrHV7oMh71Ke9XPY59oA2G1MKVkcBJfzq"
    };

    /**
     * Download csv template
     * @param sampleData if true, populate template with sample data
     * @return byte[]
     * @throws IOException ex
     */
    public byte[] downloadTemplate(boolean sampleData) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);

        // create CSVWriter object fileWriter object as parameter
        CSVWriter writer = new CSVWriter(streamWriter);
        writer.writeNext(FILE_HEADER);

        if ( sampleData ) {

            // populate csv file with sample data
            writer.writeNext(SAMPLE_DATA);
        }

        streamWriter.flush();
        writer.close();

        return stream.toByteArray();
    }

    /**
     * Create TransferRequests from CSV file
     * @param file MultipartFile
     * @return ResponseEntity
     */
    @Transactional
    public ResponseEntity<List<TransferRequest>> elaborateFile(MultipartFile file) {

        List<TransferRequest> transferRequestList = new ArrayList<>();

        log.info("Parsing file data...");

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                String[] data = line.split(",");

                if ( this.sanitizeData(data[0]).equals(FILE_HEADER[0])
                        || this.sanitizeData(data[2]).equals(SAMPLE_DATA[2])) {
                    continue;
                }

                // create TransferRequest

                BigDecimal amount = new BigDecimal(this.sanitizeData(data[0]));
                Currency currency = Currency.valueOf(this.sanitizeData(data[1]));
                String receivingAddress = this.sanitizeData(data[2]);

                TransferRequest request = new TransferRequest(currency, amount, receivingAddress);
                transferRequestList.add(request);
            }

            transferRequestList = transferRequestService.bulkCreateTransferRequests(transferRequestList);
            return new ResponseEntity<>(transferRequestList, HttpStatus.OK);

        } catch (Exception ex) {

            log.error("Unable to elaborate file with error {}", ex.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    private String sanitizeData(String data) {
        return data.replace("\"", "").trim();
    }
}
