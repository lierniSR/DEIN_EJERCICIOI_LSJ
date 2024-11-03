package es.liernisarraoa.gestionpersonasfiltro.Modelo;

/**
 * Clase Personas para utilizar como objeto.
 *
 * @author Lierni
 * @version 1.0
 */
public class Personas {
    private Integer id = 1;
    private String nombre;
    private String apellido;
    private Integer edad;

    /**
     * Constructor que inicializa una persona con su nombre, apellido y edad.
     *
     * @param nombre  el nombre de la persona
     * @param apellido el apellido de la persona
     * @param edad    la edad de la persona
     */
    public Personas(Integer id, String nombre, String apellido, Integer edad){
        this.id  = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return el nombre de la persona
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return el apellido de la persona
     */
    public String getApellido(){
        return apellido;
    }

    /**
     * Obtiene la edad de la persona.
     *
     * @return la edad de la persona
     */
    public Integer getEdad(){
        return edad;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param nombre el nuevo nombre de la persona
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Establece el apellido de la persona.
     *
     * @param apellido el nuevo apellido de la persona
     */
    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    /**
     * Establece la edad de la persona.
     *
     * @param edad la nueva edad de la persona
     */
    public void setEdad(Integer edad){
        this.edad = edad;
    }
    /**
     * Obtiene el Identificador de la persona.
     *
     * @return el identificador de la persona
     */
    public Integer getId(){
        return id;
    }

    /**
     * Compara si dos objetos Personas son iguales.
     * Dos objetos Personas son iguales si tienen el mismo nombre, apellido y edad.
     *
     * @param obj el objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) obj;
        return nombre.equals(other.nombre) && apellido.equals(other.apellido) && edad == other.edad;
    }
}
