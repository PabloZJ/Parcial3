package com.tech_nova.tech_nova.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.service.TipoPagoService;

@RestController
@RequestMapping("/api/v1/tipo-pagos")
public class TipoPagoController {
    @Autowired
    private TipoPagoService tipoPagoService;

    @GetMapping
    public ResponseEntity<List<TipoPago>> listar() {
        List<TipoPago> tipoPagos = tipoPagoService.obtenerTipoPagos();
        if(tipoPagos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipoPagos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoPago> buscartipoPagoPorId(@PathVariable Long id) {
        TipoPago tipoPago = tipoPagoService.obtenerTipoPagoPorId(id);
        if(tipoPago == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipoPago);
    }
    @PostMapping
    public ResponseEntity<TipoPago> guardar(@RequestBody TipoPago tipoPago){
        TipoPago nuevoTipoPago = tipoPagoService.guardarTipoPago(tipoPago);
        return ResponseEntity.status(200).body(nuevoTipoPago);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TipoPago> actualizar(@PathVariable Long id, @RequestBody TipoPago tipoPago){
        TipoPago tipoPagoActualizado = tipoPagoService.actualizarTipoPago(id, tipoPago);
        if (tipoPagoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoPagoActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<TipoPago> editar(@PathVariable Long id, @RequestBody TipoPago tipoPago){
        TipoPago tipoPagoActualizado = tipoPagoService.actualizarTipoPagoParcial(id, tipoPago);
        if (tipoPagoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoPagoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        tipoPagoService.eliminarTipoPago(id);
        return ResponseEntity.noContent().build();
    }
}
