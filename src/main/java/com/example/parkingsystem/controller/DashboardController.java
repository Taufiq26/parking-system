package com.example.parkingsystem.controller;

import java.util.List;

import com.example.parkingsystem.repository.ParkingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private ParkingRepository parkingRepository;
        
    @GetMapping("/dashboard")
	public String dashboard(Model model) {

        List<Object[]> parking_list = parkingRepository.findWhoStillParking();
        
        model.addAttribute("parking_list", parking_list);
        return "dashboard";
	}
}
