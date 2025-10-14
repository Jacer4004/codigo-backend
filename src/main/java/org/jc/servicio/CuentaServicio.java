package org.jc.servicio;

import org.jc.entidad.CuentaEntidad;
import org.jc.repositorio.BaseRepositorio;
import org.jc.repositorio.CuentaRepositorio;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CuentaServicio extends BaseSevicio<CuentaEntidad, Long> {

    @Inject
    CuentaRepositorio cuentaRepositorio;

    @Override
    public BaseRepositorio<CuentaEntidad, Long> entidad(){
        return cuentaRepositorio;
    }



}
