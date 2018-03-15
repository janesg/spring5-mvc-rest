package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    private static final String NAME = "Bob";

    @Test
    public void vendorToVendorDTO() {

        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = VendorMapper.INSTANCE.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        VendorDTO vendorDto = new VendorDTO();
        vendorDto.setName(NAME);

        Vendor vendor = VendorMapper.INSTANCE.vendorDTOToVendor(vendorDto);

        assertEquals(NAME, vendor.getName());
    }
}