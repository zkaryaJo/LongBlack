package com.longblack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longblack.config.Role;
import com.longblack.domain.Member;
import com.longblack.domain.Store;
import com.longblack.repository.StoreRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public Store createStore(Store store, Member member) {
        if (member.getRole() != Role.ROLE_OWNER) {
            throw new IllegalArgumentException("Only owners can create a store.");
        }
        store.setOwner(member);
        return storeRepository.save(store);
    }

    public Store updateStore(Long id, Store storeDetails) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
        store.setName(storeDetails.getName());
        store.setPhone(storeDetails.getPhone());
        store.setAddress(storeDetails.getAddress());
        return storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
        storeRepository.delete(store);
    }
}
