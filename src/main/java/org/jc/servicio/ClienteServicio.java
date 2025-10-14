package org.jc.servicio;

import org.jc.entidad.ClienteEntidad;
import org.jc.repositorio.BaseRepositorio;
import org.jc.repositorio.ClienteRepositorio;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClienteServicio extends BaseSevicio<ClienteEntidad, Long> {

    @Inject
     ClienteRepositorio clienteRepositorio;

    @Override
    public BaseRepositorio<ClienteEntidad, Long> entidad() {
        return clienteRepositorio;
    }
    
}
