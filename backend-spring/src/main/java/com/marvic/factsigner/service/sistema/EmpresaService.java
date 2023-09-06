package com.marvic.factsigner.service.sistema;

import com.marvic.factsigner.payload.sistema.CertificateDTO;
import com.marvic.factsigner.payload.sistema.EmpresaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface EmpresaService {

    void setupCertificate(CertificateDTO dto, InputStream file, String empresaId);

    EmpresaDTO getOne(String id);

    List<EmpresaDTO> getMany();

    EmpresaDTO create(EmpresaDTO dto);

    void delete(String id);

    EmpresaDTO update(EmpresaDTO dto, String id);
}
