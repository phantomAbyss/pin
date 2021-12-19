package com.pinche.service;

import com.google.common.base.Preconditions;
import com.pinche.domain.address.AddressDO;
import com.pinche.domain.address.Dot;
import com.pinche.domain.address.GeoAddress;
import com.pinche.domain.address.PublishResult;
import com.pinche.infrastructure.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
@Service
public class AddressService {
    @Autowired

    private AddressRepository addressRepository;

    public PublishResult publish(GeoAddress start, GeoAddress end) {
        PublishResult result = new PublishResult();
        Integer startId = solve(start, false);
        Integer endId = solve(end, true);
        result.setStartId(startId);
        result.setEndId(endId);
        return result;
    }

    public GeoAddress queryGeoAddress(Integer id) {
        AddressDO addressDO = addressRepository.findById(id);
        Preconditions.checkState(addressDO != null, "address query null");
        return assemble(addressDO);
    }

    private Integer solve(GeoAddress start, Boolean isEnd) {
        AddressDO oldDo = addressRepository.find(start.getDot());
        Integer startId;
        if (oldDo == null) {
            AddressDO addressDO = assemble(start, isEnd);
            addressRepository.insert(addressDO);
            startId = addressDO.getId();
        } else {
            addressRepository.addPoint(oldDo.getId());
            startId = oldDo.getId();
        }
        return startId;
    }

    private AddressDO assemble(GeoAddress address, Boolean isEnd) {
        AddressDO addressDO = new AddressDO();
        addressDO.setName(address.getName());
        addressDO.setAddress(address.getAddress());
        addressDO.setLatitude(address.getDot().getLatitude());
        addressDO.setLongitude(address.getDot().getLongitude());
        addressDO.setType(isEnd);
        return addressDO;
    }

    private GeoAddress assemble(AddressDO addressDO) {
        GeoAddress geoAddress = new GeoAddress();
        geoAddress.setName(addressDO.getName());
        geoAddress.setAddress(addressDO.getAddress());
        geoAddress.setDot(new Dot(addressDO.getLatitude(), addressDO.getLongitude()));
        return geoAddress;

    }
}
