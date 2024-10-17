# XavierGranda_Practica5

## Descripción
**Filmoteca** es una aplicación Android diseñada para gestionar y explorar una colección de películas. La aplicación permite a los usuarios agregar, editar, y eliminar películas de su lista personal. Además, los usuarios pueden seleccionar múltiples películas para realizar acciones en ellas, como la eliminación, y navegar a través de una interfaz intuitiva y moderna.

## Funcionalidades Clave

- **Visualización de Películas:** Muestra una lista de películas con detalles como título, director y una imagen representativa.
- **Selección Múltiple:** Permite seleccionar varias películas a la vez para realizar acciones masivas, como la eliminación.
- **Gestión de Películas:** Los usuarios pueden añadir nuevas películas a la lista, editar la información de las existentes y eliminarlas cuando lo deseen.
- **Navegación Intuitiva:** La aplicación cuenta con una barra de navegación que permite regresar a la pantalla principal fácilmente desde cualquier actividad.
- **Interfaz Amigable:** Utiliza componentes de Material Design para ofrecer una experiencia de usuario atractiva y fácil de usar.

## 1. Problema con la visualización del menú en `FilmListActivity`

**Inconveniente:**
El menú de opciones no aparecía en la `FilmListActivity` tras crear y configurar el archivo `menu_action_mode.xml`.

**Solución:**
- Se verificó que el archivo `menu_action_mode.xml` estaba en el directorio `res/menu`.
- Se aseguró que el método `onCreateOptionsMenu` inflara correctamente el menú.
- Finalmente, se implementó correctamente el `ActionMode` para gestionar la selección múltiple y las acciones del menú.

## 2. Selección múltiple de películas no funcionaba

**Inconveniente:**
No se podía seleccionar múltiples películas para realizar acciones en ellas, a pesar de haber configurado el `ListView` con `android:choiceMode="multipleChoice"`.

**Solución:**
- Se añadió la lógica en el adaptador `FilmAdapter` para manejar la selección múltiple, utilizando una lista `SparseBooleanArray` para almacenar el estado de selección de cada elemento.
- También se implementó el método `toggleSelection` en el adaptador para alternar el estado de selección de los elementos, permitiendo que los usuarios seleccionen y deseleccionen las películas correctamente.

## 3. Al seleccionar películas, mostraba "0 seleccionadas"

**Inconveniente:**
Después de seleccionar películas en el `ListView`, la aplicación seguía mostrando "0 seleccionadas" en el `ActionMode`.

**Solución:**
- Se corrigió la forma en que se contaban las películas seleccionadas en el `ActionMode` al utilizar correctamente el método `getSelectedCount()` en el adaptador.
- Se actualizó el título de `ActionMode` cada vez que se seleccionaba o deseleccionaba una película para reflejar el número correcto de películas seleccionadas.

## 4. Eliminar elementos no funcionaba correctamente

**Inconveniente:**
Al eliminar películas seleccionadas, estas no se removían del `ListView`, ya que el método `remove` del adaptador llamaba recursivamente a sí mismo.

**Solución:**
- Se corrigió el método `remove(film: Film)` para eliminar el objeto de la lista interna del adaptador sin llamar al mismo método de forma recursiva.
- Además, se añadió una validación para verificar si la película existía en la lista antes de intentar eliminarla.

## 5. Tamaño del icono del botón de navegación

**Inconveniente:**
El icono de navegación (back button) en la `Toolbar` se mostraba demasiado grande, lo que afectaba la estética de la aplicación.

**Solución:**
- Se ajustó el tamaño del icono usando el atributo `android:scaleType="fitCenter"` dentro del archivo de diseño de la `Toolbar`.
- También se optimizó el recurso drawable del icono, asegurando que estaba en el tamaño adecuado dentro de los directorios de recursos (`drawable-mdpi`, `drawable-hdpi`, etc.).

## 6. Mantener la lista de películas seleccionadas al utilizar el botón de retroceso

**Inconveniente:**
Al presionar el botón de retroceso en la `Toolbar`, se perdía la lista de películas seleccionadas o cualquier otra modificación realizada en la actividad.

**Solución:**
- Se sobrescribió el método `onBackPressed()` para asegurar que, antes de volver a la actividad anterior, los datos de las películas seleccionadas se pasaran correctamente a través de un `Intent`.
- En la actividad que llamó, se implementó el método `onActivityResult()` para restaurar la lista de películas seleccionadas al recibir el resultado.
