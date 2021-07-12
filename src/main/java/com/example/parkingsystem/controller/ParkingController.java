package com.example.parkingsystem.controller;

import com.example.parkingsystem.repository.ParkingRepository;
import com.example.parkingsystem.repository.VehicleTypeRepository;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import com.example.parkingsystem.model.Parking;
import com.example.parkingsystem.model.VehicleType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ParkingController {
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private VehicleTypeRepository vtRepository;

    @GetMapping("/parking-in")
	public String parkingIn() {
        return "parking_in";
	}

    @PostMapping("/parking-in/process") // Map ONLY POST Requests
    public RedirectView parkingInProcess (
        @RequestParam Integer vehicle_type,
        @RequestParam String police_number
    ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Parking p = new Parking();
        p.setVehicleType(vehicle_type);
        p.setPoliceNumber(police_number);
        p.setParkingIn(new Timestamp(System.currentTimeMillis()));
        p.setParkingOut(null);
        parkingRepository.save(p);

        return new RedirectView("/");
    }

    @GetMapping("/parking-out")
	public String parkingOut() {
        return "parking_out";
	}

    @PostMapping("/parking-out/process")
    public RedirectView parkingOutProcess (
        @RequestParam String police_number
    ) {
        Parking p = parkingRepository.findOneWhoStillParking(police_number);
        parkingRepository.doParkingOut(police_number, new Timestamp(System.currentTimeMillis()));

        Integer id = 0;
        if (p != null)
            id = p.getId();

        return new RedirectView("/parking/result?id="+id+"&f=i");
    }

    @GetMapping("/parking/result")
    public String parkingResult (
        @RequestParam Integer id,
        @RequestParam String f,
        Model model
    ) {
        Optional<Parking> p = parkingRepository.findById(id);

        if (p.isPresent()) {
            Long diff = p.get().getParkingOut().getTime() - p.get().getParkingIn().getTime();
            Long duration = (long) Math.ceil((double) diff / (60 * 60 * 1000));

            Optional<VehicleType> vt = vtRepository.findById(p.get().getVehicleType());
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);
            String total_price = formatter.format(vt.get().getBasePrice() * duration);

            model.addAttribute("message", "Anda telah parkir selama "+ duration +" jam.");
            model.addAttribute("total_price", "Rp. "+ total_price +",-");
        } else {
            model.addAttribute("message", "Data Tidak Ditemukan!");
            model.addAttribute("total_price", "");
        }

        if (f.equals("d"))
            model.addAttribute("redirect", "/dashboard");
        else
            model.addAttribute("redirect", "/");

        return "parking_result";
    }


    // Dashboard
    @GetMapping("/dashboard/parking-in")
	public String dashboardParkingIn() {
        return "dashboard_parking_in";
	}

    @PostMapping("/dashboard/parking-in/process") // Map ONLY POST Requests
    public RedirectView dashboardParkingInProcess (
        @RequestParam Integer vehicle_type,
        @RequestParam String police_number
    ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Parking p = new Parking();
        p.setVehicleType(vehicle_type);
        p.setPoliceNumber(police_number);
        p.setParkingIn(new Timestamp(System.currentTimeMillis()));
        p.setParkingOut(null);
        parkingRepository.save(p);

        return new RedirectView("/dashboard");
    }

    @GetMapping("/dashboard/parking-out/process")
    public RedirectView dashboardParkingOutProcess (
        @RequestParam Integer id
    ) {

        parkingRepository.doParkingOutById(id, new Timestamp(System.currentTimeMillis()));

        return new RedirectView("/parking/result?id="+id+"&f=d");
    }
}