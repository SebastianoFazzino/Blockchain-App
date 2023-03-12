package com.blockchain.app.mappers;

import com.blockchain.app.entities.TransferRequestEntity;
import com.blockchain.app.models.dtos.TransferRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferRequestMapper extends BaseMapper<TransferRequestEntity, TransferRequest> {

}
