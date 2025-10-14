package org.jc.servicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jc.entidad.CuentaEntidad;
import org.jc.entidad.MovimientoEntidad;
import org.jc.repositorio.BaseRepositorio;
import org.jc.repositorio.MovimientoRepositorio;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MovimientoServicio extends BaseSevicio<MovimientoEntidad, Long> {

    @Inject
    MovimientoRepositorio movimientoRepositorio;

    @Override
    public BaseRepositorio<MovimientoEntidad, Long> entidad() {
        return movimientoRepositorio;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public String movimientos(CuentaEntidad c, double valor, String tipo) {
        String mensaje = "";
        Date fechaActual = new Date();

        Query sql = movimientoRepositorio.getEntityManager()
                .createQuery(" Select p  from MovimientoEntidad p where p.idCuenta =: cuenta order by p.fecha DESC");

        sql.setParameter("cuenta", c);
        Double saldo;
        List<MovimientoEntidad> movimiento = sql.getResultList();
        MovimientoEntidad m = new MovimientoEntidad();

        m.setTipoMovimiento(tipo);
        m.setFecha(fechaActual);
        m.setIdCuenta(c);
        m.setValor(valor);
        saldo = 0.0;
        if (movimiento.size() == 0) {
            m.setSaldo(c.getSaldoInicial());
            if (tipo == "CREDITO") {
                guardar(m);
            } else {
                if (m.getSaldo() > m.getValor()) {
                    saldo = m.getSaldo() - m.getValor();
                    m.setSaldo(Math.round(saldo * 100.0) / 100.0);
                    guardar(m);
                }
                mensaje = "Saldo insuficiente";
            }
        } else {

            if (tipo.equals("CREDITO") && movimiento.get(0).getValor() >= 0) {
                saldo = movimiento.get(0).getSaldo() + m.getValor();
                m.setSaldo(Math.round(saldo * 100.0) / 100.0);

                movimientoRepositorio.getEntityManager().persist(m);

            } else {
                if (movimiento.get(0).getSaldo() > m.getValor()) {
                    saldo = movimiento.get(0).getSaldo() - m.getValor();
                    m.setSaldo(Math.round(saldo * 100.0) / 100.0);
                    movimientoRepositorio.getEntityManager().persist(m);
                } else {
                    if (movimiento.get(0).getValor() == 0.0) {
                        mensaje = "Saldo no disponible";
                    }
                    mensaje = "Saldo insuficiente";
                }

            }

        }
        return mensaje;
    }

    @SuppressWarnings("unchecked")
    public List<MovimientoEntidad> reporte(CuentaEntidad c, String desde, String hasta) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaDesde = (Date) dateFormat.parse(desde);
        Date fechaHasta = (Date) dateFormat.parse(hasta);
        Query sql = movimientoRepositorio.getEntityManager().createQuery(
                " Select p  from MovimientoEntidad p where p.idCuenta =:cuenta and  p.fecha BETWEEN :fechaDesde AND :fechaHasta order by p.idMovimiento ");

        sql.setParameter("cuenta", c);
        sql.setParameter("fechaDesde", fechaDesde);
        sql.setParameter("fechaHasta", fechaHasta);

        List<MovimientoEntidad> movimientos = sql.getResultList();

        return movimientos;
    }

}
