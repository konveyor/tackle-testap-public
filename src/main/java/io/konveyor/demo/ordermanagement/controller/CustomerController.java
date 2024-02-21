package io.konveyor.demo.ordermanagement.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.xml.bind.JAXB;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.konveyor.demo.ordermanagement.exception.ResourceNotFoundException;
import io.konveyor.demo.ordermanagement.model.Customer;
import io.konveyor.demo.ordermanagement.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	private static Logger logger = Logger.getLogger( CustomerController.class.getName() );
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getById(@PathVariable("id") Long id) {
		OutputStream opt = new ByteArrayOutputStream();
		Customer c = customerService.findById(id);
		if (c == null) {
			throw new ResourceNotFoundException("Requested order doesn't exist");
		}
		JAXB.marshal(c, opt);
		logger.debug("Returning element: " + c);


		return c;
	}
	
	@RequestMapping
	public Page<Customer> findAll(Pageable pageable){
		return customerService.findAll(pageable);
	}

}
