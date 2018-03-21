package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    private static final String BASE_URL = "/api/v1/vendors";
    private static final Long ID = 22L;
    private static final String VENDOR_URL = BASE_URL + "/" + ID;
    private static final String NAME = "Bobbins R Us";

    private VendorService service;

    @Mock
    private VendorRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new VendorServiceImpl(repository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() {

        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        when(repository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOS = service.getAllVendors();

        assertEquals(3, vendorDTOS.size());
    }

    @Test
    public void getVendorById() {

        Vendor vendor = new Vendor(ID, NAME);

        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = service.getVendorById(ID);

        assertEquals(NAME, vendorDTO.getName());
        assertEquals(VENDOR_URL, vendorDTO.getVendorUrl());
    }

    @Test
    public void createNewVendor() {

        Vendor vendor = new Vendor(ID, NAME);

        when(repository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO dto = service.createNewVendor(new VendorDTO());

        assertEquals(NAME, dto.getName());
        assertEquals(VENDOR_URL, dto.getVendorUrl());
    }

    @Test
    public void updateVendor() {

        Vendor vendor = new Vendor(ID, NAME);

        when(repository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO dto = service.updateVendor(ID, new VendorDTO());

        assertEquals(NAME, dto.getName());
        assertEquals(VENDOR_URL, dto.getVendorUrl());
    }

    @Test
    public void patchVendor() {

        Vendor vendor = new Vendor(ID, NAME);

        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(repository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO dto = service.patchVendor(ID, new VendorDTO());

        assertEquals(NAME, dto.getName());
        assertEquals(VENDOR_URL, dto.getVendorUrl());
    }

    @Test
    public void deleteVendor() {

        service.deleteVendor(ID);

        verify(repository).deleteById(anyLong());
    }

}