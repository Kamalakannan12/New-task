package com.example.ecommerce.service;

import com.example.ecommerce.entity.Invoice;
import com.example.ecommerce.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepo;
    public Invoice checkbyid(Long id) {
        return invoiceRepo.findById(id).orElse(null);

    }
}
