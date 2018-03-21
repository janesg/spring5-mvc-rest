package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "REST API for managing vendors")
@RestController
@RequestMapping("/api/v1/vendors")
@AllArgsConstructor
public class VendorController {

    private VendorService service;

    @ApiOperation(value = "Get a list of all vendors", notes = "List returned is ordered according to creation")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(service.getAllVendors());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable("id") Long id) {
        return service.getVendorById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return service.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable("id") Long id,
                                  @RequestBody VendorDTO vendorDTO) {
        return service.updateVendor(id, vendorDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable("id") Long id,
                                 @RequestBody VendorDTO vendorDTO) {
        return service.patchVendor(id, vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Void deleteVendor(@PathVariable("id") Long id) {
        service.deleteVendor(id);
        return null;
    }

}
