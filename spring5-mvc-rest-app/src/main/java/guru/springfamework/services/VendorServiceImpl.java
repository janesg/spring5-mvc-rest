package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private static final String BASE_URL = "/api/v1/vendors";

    private final VendorRepository repository;
    private final VendorMapper mapper;

    @Override
    public List<VendorDTO> getAllVendors() {
        return repository.findAll().stream()
                .map(vendor -> {
                    VendorDTO dto = mapper.vendorToVendorDTO(vendor);
                    dto.setVendorUrl(BASE_URL + "/" + vendor.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {

        Optional<Vendor> optVendor = repository.findById(id);

        if (optVendor.isPresent()) {
            VendorDTO vendorDTO = mapper.vendorToVendorDTO(optVendor.get());
            vendorDTO.setVendorUrl(BASE_URL + "/" + id);
            return vendorDTO;
        } else {
            throw new ResourceNotFoundException("Vendor not found : id = " + id);
        }
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        return saveVendor(mapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {

        Vendor vendor = mapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return saveVendor(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        Optional<Vendor> optVendor = repository.findById(id);

        if (optVendor.isPresent()) {
            Vendor existing = optVendor.get();

            if (vendorDTO.getName() != null) {
                existing.setName(vendorDTO.getName());
            }

            return saveVendor(existing);

        } else {
            throw new ResourceNotFoundException("Vendor not found : id = " + id);
        }
    }

    @Override
    public void deleteVendor(Long id) {

        repository.deleteById(id);
    }

    private VendorDTO saveVendor(Vendor vendor) {
        Vendor savedVendor = repository.save(vendor);

        VendorDTO dto = mapper.vendorToVendorDTO(savedVendor);
        dto.setVendorUrl(BASE_URL + "/" + savedVendor.getId());

        return dto;
    }
}
