package com.umg.carrito.controller;

import com.umg.carrito.model.CarritoItem;
import com.umg.carrito.model.Producto;
import com.umg.carrito.repository.CarritoItemRepository;
import com.umg.carrito.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @GetMapping("/")
    public String listarProductos(Model model) {

        model.addAttribute("productos", productoRepository.findAll());

        return "index";
    }

    @GetMapping("/agregar/{id}")
    public String agregarCarrito(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null && producto.getStock() > 0) {

            CarritoItem itemExistente = carritoItemRepository.findByProductoId(id);

            if (itemExistente != null) {

                if (itemExistente.getCantidad() < producto.getStock()) {

                    itemExistente.setCantidad(itemExistente.getCantidad() + 1);

                    itemExistente.setSubtotal(
                            itemExistente.getCantidad() * producto.getPrecio()
                    );

                    carritoItemRepository.save(itemExistente);
                }

            } else {

                CarritoItem item = new CarritoItem();

                item.setProducto(producto);
                item.setCantidad(1);
                item.setSubtotal(producto.getPrecio());

                carritoItemRepository.save(item);
            }

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Producto agregado al carrito"
            );
        }

        return "redirect:/";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model) {

        List<CarritoItem> items = carritoItemRepository.findAll();

        double total = 0;

        for (CarritoItem item : items) {
            total += item.getSubtotal();
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "carrito";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCarrito(@PathVariable Long id) {

        carritoItemRepository.deleteById(id);

        return "redirect:/carrito";
    }

    @GetMapping("/sumar/{id}")
    public String sumarCantidad(@PathVariable Long id) {

        CarritoItem item = carritoItemRepository.findById(id).orElse(null);

        if (item != null) {

            Producto producto = item.getProducto();

            if (item.getCantidad() < producto.getStock()) {

                item.setCantidad(item.getCantidad() + 1);

                item.setSubtotal(
                        item.getCantidad() * producto.getPrecio()
                );

                carritoItemRepository.save(item);
            }
        }

        return "redirect:/carrito";
    }

    @GetMapping("/restar/{id}")
    public String restarCantidad(@PathVariable Long id) {

        CarritoItem item = carritoItemRepository.findById(id).orElse(null);

        if (item != null) {

            if (item.getCantidad() > 1) {

                item.setCantidad(item.getCantidad() - 1);

                item.setSubtotal(
                        item.getCantidad() * item.getProducto().getPrecio()
                );

                carritoItemRepository.save(item);

            } else {

                carritoItemRepository.deleteById(id);
            }
        }

        return "redirect:/carrito";
    }

    @GetMapping("/comprar")
    public String comprar(Model model) {

        List<CarritoItem> items = carritoItemRepository.findAll();

        for (CarritoItem item : items) {

            Producto producto = item.getProducto();

            int nuevoStock = producto.getStock() - item.getCantidad();

            if (nuevoStock < 0) {
                nuevoStock = 0;
            }

            producto.setStock(nuevoStock);

            productoRepository.save(producto);
        }

        carritoItemRepository.deleteAll();

        model.addAttribute(
                "mensaje",
                "¡Compra realizada con exito!"
        );

        model.addAttribute(
                "items",
                carritoItemRepository.findAll()
        );

        model.addAttribute("total", 0);

        return "carrito";
    }
}