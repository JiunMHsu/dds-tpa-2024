package ar.edu.utn.frba.dds.utils;

import io.javalin.http.Context;

/**
 * Interfaz para manejar las vistas de un CRUD.
 */
//TODO check mayuscula
public interface ICrudViewsHandler {

  /**
   * Pretende devolver una vista que contenga a todos los recursos.
   * La ruta asociada debería ser GET /recurso
   *
   * @param context Objeto Context de io.javalin.http
   */
  void index(Context context);

  /**
   * Recibe de path param el ID de un recurso y
   * pretende devolver una vista con el detalle de dicho recurso.
   * La ruta asociada debería ser GET /recurso/{id}
   *
   * @param context Objeto Context de io.javalin.http
   */
  void show(Context context);

  /**
   * Pretende devolver una vista con un formulario para dar de alta un nuevo recurso.
   * La ruta asociada debería ser GET /recurso/nuevo
   *
   * @param context Objeto Context de io.javalin.http
   */
  void create(Context context);

  /**
   * Recibe los datos del recurso a crear y pretende dar de alta a dicho recurso.
   * La ruta asociada debería ser POST /recurso
   *
   * @param context Objeto Context de io.javalin.http
   */
  void save(Context context);

  /**
   * Pretende devolver una vista con un formulario que permita
   * editar al recurso que llega de path param.
   * La ruta asociada debería ser GET /recurso/{id}/edit
   *
   * @param context Objeto Context de io.javalin.http
   */
  void edit(Context context);

  /**
   * Recibe los datos del recurso modificado y pretende registrar dicha modificación.
   * La ruta asociada debería ser POST /recurso/{id}/edit
   *
   * @param context Objeto Context de io.javalin.http
   */
  void update(Context context);

  /**
   * Recibe el ID del recurso a eliminar y pretende dar de baja a dicho recurso.
   * La ruta asociada debería ser POST /recurso/{id}/delete
   *
   * @param context Objeto Context de io.javalin.http
   */
  void delete(Context context);
}
