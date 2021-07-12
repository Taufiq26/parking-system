package com.example.parkingsystem.controller;

import java.util.List;
import java.util.Optional;

import com.example.parkingsystem.model.VehicleType;
import com.example.parkingsystem.repository.VehicleTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class VehicleTypeController {
    @Autowired
    private VehicleTypeRepository vtRepository;
    
    @GetMapping("/dashboard/vehicle-type")
	public String vehicleTypeList(Model model) {
        List<VehicleType> vt = (List<VehicleType>) vtRepository.findAll();

        model.addAttribute("vehicle_type", vt);
        return "vehicle_type";
	}

    @GetMapping("/dashboard/vehicle-type/create")
    public String vehicleTypeCreate () {
        return "vehicle_type_create";
    }

    @PostMapping("/dashboard/vehicle-type/store")
    public RedirectView vehicleTypeStore (
        @RequestParam String name,
        @RequestParam Integer base_price
    ) {
        vtRepository.save(new VehicleType(name, base_price));
        return new RedirectView("/dashboard/vehicle-type");
    }

    @GetMapping("/dashboard/vehicle-type/edit")
    public String vehicleTypeEdit (
        @RequestParam Integer id,
        Model model
    ) {
        Optional<VehicleType> vt = vtRepository.findById(id);
        model.addAttribute("vehicle_type", vt.get());
        
        return "vehicle_type_edit";
    }

    @PostMapping("/dashboard/vehicle-type/update")
    public RedirectView vehicleTypeUpdate (
        @RequestParam Integer id,
        @RequestParam String name,
        @RequestParam Integer base_price
    ) {
        Optional<VehicleType> vtData = vtRepository.findById(id);
        if (vtData.isPresent()) {
            VehicleType vt = vtData.get();
            vt.setName(name);
            vt.setBasePrice(base_price);
            vtRepository.save(vt);
        }

        return new RedirectView("/dashboard/vehicle-type");
    }

    @GetMapping("/dashboard/vehicle-type/delete")
    public RedirectView vehicleTypeDelete (
        @RequestParam Integer id
    ) {
        vtRepository.deleteById(id);
        return new RedirectView("/dashboard/vehicle-type");
    }
}
