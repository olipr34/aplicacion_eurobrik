package com.example.lectorqr;

public class trabajador {

    private String nombre;
    private String apellidos;
    private String comunidad;
    private String localizacion;
    private String trabajo;
    private String fichaje;
    private String fecha;
    private String hora;

    public trabajador(String nombre, String apellidos, String comunidad, String localizacion, String trabajo,String fichaje, String fecha, String hora) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.comunidad = comunidad;
        this.localizacion = localizacion;
        this.trabajo = trabajo;
        this.fichaje= fichaje;
        this.fecha = fecha;
        this.hora = hora;
    }
        public String getNombre () {
            return nombre;
        }

        public void setNombre (String nombre){
            this.nombre = nombre;
        }

        public String getApellidos () {
            return apellidos;
        }

        public void setApellidos (String apellidos){
            this.apellidos = apellidos;
        }

        public String getComunidad () {
            return comunidad;
        }

        public void setComunidad (String comunidad){
            this.comunidad = comunidad;
        }

        public String getLocalizacion() {
            return localizacion;
        }

        public void setLocalizacion (String localizacion){
            this.localizacion = localizacion;
        }

        public String getTrabajo () {
            return trabajo;
        }

        public void setTrabajo (String trabajo){
            this.trabajo = trabajo;
        }

        public String getFichaje(){return fichaje;}
        public void setFichaje(String fichaje){this.fichaje=fichaje;}


        public String getFecha () {
            return fecha;
        }

        public void setFecha (String fecha){
            this.fecha = fecha;
        }

        public String getHora () {
            return hora;
        }

        public void setHora (String hora){
            this.hora = hora;
        }




}
