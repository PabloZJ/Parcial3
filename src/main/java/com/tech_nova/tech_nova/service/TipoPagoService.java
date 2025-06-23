package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.repository.BoletaRepository;
import com.tech_nova.tech_nova.repository.TipoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    //Obtener TipoPagos
    public List<TipoPago> obtenerTipoPagos(){
        return tipoPagoRepository.findAll();
    }

    //Obtener TipoPago por id
    public TipoPago obtenerTipoPagoPorId (Long id){
        return tipoPagoRepository.findById(id).orElse(null);
    }

    //Guardar TipoPago
    public TipoPago guardarTipoPago (TipoPago TipoPago){
        return tipoPagoRepository.save(TipoPago);

    }
    //Eliminar TipoPago
    public void eliminarTipoPago(Long id){
        TipoPago tipoPago = tipoPagoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo pago no encontrado"));
        List<Boleta> boletas = boletaRepository.findByTipoPago(tipoPago);
        for(Boleta boleta : boletas) {
            boletaRepository.delete(boleta);
        }
        tipoPagoRepository.delete(tipoPago);
    }
    //ActualizarTodo
    public TipoPago actualizarTipoPago(Long id, TipoPago TipoPago){
        TipoPago tipoPagoExistente = tipoPagoRepository.findById(id).orElse(null);
        if (tipoPagoExistente != null){
            tipoPagoExistente.setNombre(TipoPago.getNombre());
            return tipoPagoRepository.save(tipoPagoExistente);
        }
        else{
            return null;
        }
    }
    //ActualizarPath
    public TipoPago actualizarTipoPagoParcial(Long id, TipoPago TipoPago){
    TipoPago tipoPagoExistente = tipoPagoRepository.findById(id).orElse(null);
    if (tipoPagoExistente != null){
        if(TipoPago.getNombre() != null)
            tipoPagoExistente.setNombre(TipoPago.getNombre());
        return tipoPagoRepository.save(tipoPagoExistente);
    }
    else{
        return null;
    }
    }
}
