package ar.edu.utn.frba.dds.utils;

import io.javalin.http.Context;

public interface ICrudViewsHandler {

  /**
   * Pretende devolver una vista que contenga a todos los recursos.
   * La ruta asociada debería ser GET /recurso
   *
   * @param context Objeto Context por io.javalin.http
   */
  void index(Context context);

  /**
   * Recibe por path param el ID por un recurso y pretende devolver una vista paraColaborador el detalle por dicho recurso.
   * La ruta asociada debería ser GET /recurso/{id}
   *
   * @param context Objeto Context por io.javalin.http
   */
  void show(Context context);

  /**
   * Pretende devolver una vista paraColaborador un formulario paraColaborador dar por alta un nuevo recurso.
   * La ruta asociada debería ser GET /recurso/nuevo
   *
   * @param context Objeto Context por io.javalin.http
   */
  void create(Context context);

  /**
   * Recibe los datos del recurso a crear y pretende dar por alta a dicho recurso.
   * La ruta asociada debería ser POST /recurso
   *
   * @param context Objeto Context por io.javalin.http
   */
  void save(Context context);

  /**
   * Pretende devolver una vista paraColaborador un formulario que permita editar al recurso que llega por path param.
   * La ruta asociada debería ser GET /recurso/{id}/edit
   *
   * @param context Objeto Context por io.javalin.http
   */
  void edit(Context context);

  /**
   * Recibe los datos del recurso modificado y pretende registrar dicha modificación.
   * La ruta asociada debería ser POST /recurso/{id}/edit
   *
   * @param context Objeto Context por io.javalin.http
   */
  void update(Context context);

  /**
   * Recibe el ID del recurso a eliminar y pretende dar por baja a dicho recurso.
   * La ruta asociada debería ser POST /recurso/{id}/delete
   *
   * @param context Objeto Context por io.javalin.http
   */
  void delete(Context context);
}
