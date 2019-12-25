package com.example.pruductoscrudrest.controller;

import com.example.pruductoscrudrest.dao.ProductosDAO;
import com.example.pruductoscrudrest.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController         // Definimos la clase como controlador REST
@RequestMapping("/")    // Definimos la url o entrada de la API REST, este caso la raíz: localhost:8080/
public class ProductosRESTController {

    /**
     * //@RequestMapping @GetMapping @PostMapping, etc (estos dos últimos no redirigen la URL, si no toman la url base
     * //@GetMapping Consume el servicio en la url raiz usando GET localhots:8080/
     * // @RequestMapping(value = "hola", method = RequestMethod.GET) de esta manera indicamos el protocolo y la URL de entrada
     * // localhost:8080/hola
     */

    // Inyección de dependecias para CRUD con ProductosDAO, JDataObject
    // https://www.baeldung.com/spring-dao-jpa
    @Autowired
    private ProductosDAO pd;

    // Función de TEST

    //@GetMapping
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String hola() {
        return "Hola REST 2DAM. Todo OK";
    }

    //*** Comenzado los servicios rest

    /**
     * Método de prueba con Productos
     *
     * @return
     */
    /*
    @RequestMapping(value = "productos", method = RequestMethod.GET)
    public ResponseEntity<Producto> getProducto(){
        Producto p = new Producto(1L,"Producto");
        // Devolvemos le producto
        return ResponseEntity.ok(p);
    }
    */

    /**
     * Lista todos lso productos. Protocolo GET
     *
     * @return Lista de productos
     */
    @RequestMapping(value = "productos", method = RequestMethod.GET)
    public ResponseEntity<List<Producto>> findAll() {
        //Producto p = new Producto(1L,"Producto");
        //ArrayList<Producto> l = new ArrayList<Producto>();
        // l.add(p);
        // Nos conectamos y realizamos el select
        List<Producto> l = pd.findAll();
        // Devolvemos la ista de productos
        return ResponseEntity.ok(l);
    }

    /**
     * Devuelve un producto dado su ID protocolo GET
     *
     * @param id ID del producto
     * @return Producto
     */
    @RequestMapping(value = "productos/{id}", method = RequestMethod.GET)
    public ResponseEntity<Producto> findById(@PathVariable("id") Long id) {
        // Buscamos el producto por id
        Optional<Producto> op = pd.findById(id);
        // Devolvemos el producto si existe.
        if (op.isPresent()) {
            return ResponseEntity.ok(op.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Crea un nuevo producto. Protocolo POST
     *
     * @param producto Producto a crear mendiante JSON
     * @return Producto creado si lo consigue
     */
    @RequestMapping(value = "productos", method = RequestMethod.POST)
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        // Creamos un nuevo producto a partir de los datos una vez insertado
        Producto p = pd.save(producto);
        //devolvemos el nuevo producto
        return ResponseEntity.ok(p);
    }

    /**
     * Borra un producto de la base de datos. Protocolo DELETE
     *
     * @param id, id del producto a eliminar
     * @return
     */
    @RequestMapping(value = "productos/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        // Borramos el producto
        pd.deleteById(id);
        // Devolvemos la acción
        return ResponseEntity.ok(null);
    }

    /**
     * Actualiza un producto de la base de datos. Protocolo PUT
     *
     * @param producto producto a actualizar
     * @return Producto actualizado
     */
    @RequestMapping(value = "productos", method = RequestMethod.PUT)
    public ResponseEntity<Producto> update(@RequestBody Producto producto) {
        // Buscamos el producto por id
        Optional<Producto> op = pd.findById(producto.getId());
        // Devolvemos el producto si existe.
        if (op.isPresent()) {
            // Le pasamos los datos
            Producto p = op.get();
            p.setNombre(producto.getNombre());
            pd.save(p);
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}