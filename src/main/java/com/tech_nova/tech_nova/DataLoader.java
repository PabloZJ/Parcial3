package com.tech_nova.tech_nova;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.model.Envio;
import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.BoletaRepository;
import com.tech_nova.tech_nova.repository.DetallePedidoRepository;
import com.tech_nova.tech_nova.repository.DireccionRepository;
import com.tech_nova.tech_nova.repository.EnvioRepository;
import com.tech_nova.tech_nova.repository.EstadoPedidoRepository;
import com.tech_nova.tech_nova.repository.PedidoRepository;
import com.tech_nova.tech_nova.repository.ProductoCategoriaRepository;
import com.tech_nova.tech_nova.repository.ProductoRepository;
import com.tech_nova.tech_nova.repository.TipoPagoRepository;
import com.tech_nova.tech_nova.repository.UsuarioRepository;

import net.datafaker.Faker;


@Profile("dev")
@Component
public class DataLoader  implements CommandLineRunner{
//----------------------------------------------------------------------------
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
        
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;
    
    @Autowired
    private EnvioRepository envioRepository;
    
    @Autowired
    private DireccionRepository direccionRepository;
    
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    
    @Autowired
    private BoletaRepository boletaRepository;
//----------------------------------------------------------------------------
    @Override
    public void run(String... args) throws Exception{
        //
        Faker faker = new Faker();
        Random random  = new Random();
        //-----------------------------------------------------------------------------
        for (int i = 0; i < 10; i++){
            Usuario usuario = new Usuario();
            usuario.setId(i+1);
            usuario.setNombreCompleto(faker.name().fullName());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password());
            usuario.setFechaNacimiento(faker.date().birthday(18, 90));
            usuario.setTelefono("56 9"+(faker.number().digits(8)));
            usuarioRepository.save(usuario);
        }
        //-----------------------------------------------------------------------------
        String[] tipoPagosFijos = {"Débito","Crédito","Prepago"};
        //
        for (int i = 0; i < 3; i++){
            TipoPago tipoPago = new TipoPago();
            tipoPago.setId(i+1);
            tipoPago.setNombre(tipoPagosFijos[i]);
            tipoPagoRepository.save(tipoPago);
        }
        //-----------------------------------------------------------------------------
        for (int i = 0; i < 10; i++){
            ProductoCategoria productoCategoria = new ProductoCategoria();
            productoCategoria.setId(i+1);
            productoCategoria.setNombre(faker.commerce().department());
            productoCategoria.setDescripcion(faker.lorem().sentence());
            productoCategoriaRepository.save(productoCategoria);
        }
        //-----------------------------------------------------------------------------
            List<ProductoCategoria> productoCategorias = productoCategoriaRepository.findAll();
            //
            for (int i = 0; i < 10; i++){
            Producto producto = new Producto();
            producto.setId(i+1);
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence());
            producto.setPrecio((int) Double.parseDouble(faker.commerce().price(3000,700000)));
            producto.setStock(faker.number().numberBetween(2, 50));
            producto.setProductoCategoria(productoCategorias.get(random.nextInt(productoCategorias.size())));
            productoRepository.save(producto);
        }
        //-----------------------------------------------------------------------------
            String[] tiposEstadoPedido = {"Pendiente","Cancelado","Pagado","Enviado","Entregado"};
            //
            for (int i = 0; i < 5; i++){
            EstadoPedido estadoPedido = new EstadoPedido();
            estadoPedido.setId(i+1);
            estadoPedido.setNombre(tiposEstadoPedido[i]);
            estadoPedidoRepository.save(estadoPedido);
        }
        //-----------------------------------------------------------------------------
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<EstadoPedido> estadoPedidos = estadoPedidoRepository.findAll();
        //
        for (int i = 0; i < 10; i++){
            Pedido pedido = new Pedido();
            pedido.setId(i+1);
            pedido.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            pedido.setEstadoPedido(estadoPedidos.get(random.nextInt(estadoPedidos.size())));
            pedidoRepository.save(pedido);
        }
        //-----------------------------------------------------------------------------
        for (int i = 0; i < 10; i++){
            Envio envio = new Envio();
            envio.setId(i+1);
            envio.setDescripcion(faker.lorem().sentence());
            envioRepository.save(envio);
        }
        //-----------------------------------------------------------------------------
        for (int i = 0; i < 10; i++){
            Direccion direccion = new Direccion();
            direccion.setId(i+1);
            direccion.setCalle(faker.address().streetName());
            direccion.setNumero(faker.address().buildingNumber());
            direccion.setDepartamento(faker.address().secondaryAddress());
            direccion.setCiudad(faker.address().city());
            direccion.setRegion(faker.address().state());
            direccion.setCodigoPostal(faker.address().zipCode());
            direccion.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            direccionRepository.save(direccion);
        }
        //-----------------------------------------------------------------------------
        List<Producto> productos = productoRepository.findAll();
        List<Pedido> pedidos = pedidoRepository.findAll();
        //
        for (int i = 0; i < 10; i++){
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setId(i+1);
            detallePedido.setCantidadProducto(faker.number().numberBetween(1, 5));
            detallePedido.setProducto(productos.get(random.nextInt(productos.size())));
            detallePedido.setPedido(pedidos.get(random.nextInt(pedidos.size())));
            detallePedidoRepository.save(detallePedido);
        }
        //-----------------------------------------------------------------------------
        List<TipoPago> tipoPagos = tipoPagoRepository.findAll();
        //
        for (int i = 0; i < 10; i++){
            Boleta boleta = new Boleta();
            boleta.setId(i+1);
            boleta.setTotalMonto(faker.number().numberBetween(3000, 700000));
            boleta.setFechaEmision(faker.date().past(30, TimeUnit.DAYS));
            boleta.setTipoPago(tipoPagos.get(random.nextInt(tipoPagos.size())));
            boleta.setPedido(pedidos.get(random.nextInt(pedidos.size())));
            boletaRepository.save(boleta);
        }
        //-----------------------------------------------------------------------------
    }
}
