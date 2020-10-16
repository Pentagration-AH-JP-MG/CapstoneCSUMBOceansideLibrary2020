/*
 * Copyright 2020 Marcus Gonzalez, Adam Houser, Jason Pettit
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package capstone.controller;

import capstone.domain.CheckinInfo;
import capstone.domain.CheckoutInfo;
import capstone.domain.SearchHold;
import capstone.domain.SearchItem;
import capstone.domain.Token;
import capstone.service.ItemService;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class HoldRestController {

    @Autowired
    private ItemService itemService;

    // generate token
    @PostMapping(value = "/token")
    public String getToken() {

        // debugging
        System.out.println("In token request here");

        String url = "https://opl.iii.com/iii/sierra-api/v6/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic YkdVaFlIS3RUcEFNcUJqNlc1QnkzZjRZejhVMjpjYXBzdG9uZTIwMjA=");
        headers.add("grant_type", "client_credentials");
        headers.add("Content-Type", "application/x-www-form-urlendoced");
        headers.add("Host", "Opl.iii.com");

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Token> response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);

        String token = response.getBody().getAccess_token();
        System.out.println(token);

        return token;
    }

    // update item status
    @GetMapping(value = "/updateItemStatus")
    public ResponseEntity<String> updateItem(String token, String recordNum, String status) {

        // debugging
        System.out.println("In updateItemStatus here");

        String url = "https://opl.iii.com/iii/sierra-api/v6/items/" + recordNum;
        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add("Host", "Opl.iii.com");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("status", status);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        return response;
    }

    @GetMapping("/updateItemVarfield")
    public ResponseEntity<String> setItemVarfield(String token, String recordNum, String holdID) {

        String url = "https://opl.iii.com/iii/sierra-api/v6/items/" + recordNum;
        String body = "{\"varFields\":[{\"fieldTag\":\"r\", \"content\":\"" + holdID + "\"}]}";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add("Host", "Opl.iii.com");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("status", holdID);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        return response;
    }

    @PostMapping("/api/barcodeSearch")
    public ResponseEntity<SearchItem> checkBarcode(@RequestBody String barcode) throws Exception {
        // debugging
        System.out.println("In barcode search here");

        SearchItem searchedBib = itemService.checkBarcode(barcode);

        if (searchedBib == null) {
            // testing/debug statement
            System.out.println("null response");

            // reservation not found.  Send 404 return code.
            return new ResponseEntity<SearchItem>( HttpStatus.NOT_FOUND);
        } else {
            BigInteger bib = searchedBib.getBib();
            // testing/debug statement
            System.out.println("good response");

            // return ok code and information in JSON format
            return new ResponseEntity<SearchItem>(searchedBib, HttpStatus.OK);
        }

    }

    @PostMapping("/api/barcodeItemSearch")
    public ResponseEntity<SearchItem> checkItemBarcode(@RequestBody String barcode) throws Exception {
        // debugging
        System.out.println("In barcode item search here");

        SearchItem searchedBib = itemService.checkBarcode(barcode);
        BigInteger item = searchedBib.getItem();

        // debugging
        System.out.println("check barcode called, item is " + item);

        if (item == null) {
            // testing/debug statement
            System.out.println("null response");

            // reservation not found.  Send 404 return code.
            return new ResponseEntity<SearchItem>( HttpStatus.NOT_FOUND);
        } else {
            // testing/debug statement
            System.out.println("good response");

            // return ok code and information in JSON format
            return new ResponseEntity<SearchItem>(searchedBib, HttpStatus.OK);
        }

    }

    @PostMapping("/api/getNewHold")
    public ResponseEntity<BigInteger> getNewHold(@RequestBody BigInteger patron) throws Exception {
        // debugging
        System.out.println("In getNewHold here");

        SearchHold newHold = itemService.getNewHold(patron);
        System.out.println("newHold = " + newHold.getHold());
        BigInteger newHoldID = newHold.getHold();

        if (newHoldID == null) {
            // testing/debug statement
            System.out.println("null response");

            // reservation not found.  Send 404 return code.
            return new ResponseEntity<BigInteger>( HttpStatus.NOT_FOUND);
        } else {
            System.out.println("good response");

            // return ok code and information in JSON format
            return new ResponseEntity<BigInteger>(newHoldID, HttpStatus.OK);
        }

    }

    @GetMapping("/api/NOS")
    public ResponseEntity<List<SearchItem>> getMissingItemList(String location, String bib){
        System.out.println("In get missing item");
        System.out.println("missing item bib" + bib);
        List<SearchItem> foundItems = itemService.getMissingItemList(location, bib);

        if(foundItems.size() == 0){
            System.out.println("null response");
            return new ResponseEntity<List<SearchItem>>( HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<List<SearchItem>>(foundItems, HttpStatus.OK);
        }
    }

    @GetMapping("/api/itemNOS")
    public ResponseEntity<SearchItem> getMissingItem(String bib){
        System.out.println("In get missing item");
        System.out.println("missing item bib" + bib);
        SearchItem foundItems = itemService.getMissingItem(bib);

        if(foundItems == null){
            System.out.println("null response");
            return new ResponseEntity<SearchItem>( HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<SearchItem>(foundItems, HttpStatus.OK);
        }
    }
    @PostMapping("/api/checkIn")
    public ResponseEntity<Void> checkIN(@RequestBody CheckinInfo barcode) throws Exception {
        System.out.println("in SIP check in");
        System.out.println(barcode);
        boolean response = itemService.sipCheckIn(barcode);
        if (response == true){
            System.out.println("Good response");
            return new ResponseEntity<Void>( HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/checkOut")
    public ResponseEntity<Void> checkOut(@RequestBody CheckoutInfo barcodes) throws Exception {
        System.out.println("in SIP checkOut");
        System.out.println(barcodes.getItemBarcode() + " " + barcodes.getPatronBarcode());
        boolean response = itemService.sipCheckOut(barcodes);
        if (response == true){
            System.out.println("Good response");
            return new ResponseEntity<Void>( HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}