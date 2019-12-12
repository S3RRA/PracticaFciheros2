/*
 * Práctica
 */
package clientes;
import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.RandomAccessFile;
import sun.security.util.Password;
/**
 * @author PabloSerrano
 */
public class Clientes {

    
    public static void main(String[] args) {
        //Ficheros aleatorios, cliente.txt, (DNI, Nombre, email, telefono, password)
        //No pueden ser iguales los DNI
        //Dar de alta, dar de baja, loguearte
        
        //Crear otro fichero aleatorio de productos "productos.txt" (cod_producto, nom_producto, tipo, precio_uni, stock)
        //Insertar productos, buscar por tipo, actualizar stock
        
        //Fichero secuencial, "ventas.txt" registros de ventas nuevas (cod_venta, DNI, cod_producto (1...n), cantidad)
        //cod_venta es incremental. Al loguearte como cliente vas comprando lo que necesitas hasta que no quieres más.
        //Insertar nuevas ventas, preguntamos por productos, listar ventas (cod_venta y nº de productos totales de la venta)
        
        
        //Dejaremos un espacio para las variables que queramos declarar de instancia
        
        ////////////////////////////////////////////////////////////////////////////
        
            inicio();  
    }
    
    public static void inicio(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Bienvenido,");
            System.out.println("¿A que área desea acceder? Clientes, productos, ventas: ");
            String respuesta = sc.nextLine();
            
            if(respuesta.equals("clientes") || respuesta.equals("cliente")){
                clientes();
            }
            if(respuesta.equals("productos")){
                productos();
            }
            if(respuesta.equals("ventas")){
                ventas();
            }else{
                inicio();
            }
        }
        catch(Exception e){
            System.out.println("FUNCIÓN NO ENCONTRADA");
        }
    }
    
    public static void clientes() throws FileNotFoundException, IOException {
          //nos creamos objeto file tipo fichero
        File fichero = new File ("clientes.txt");
        RandomAccessFile aa = new RandomAccessFile (fichero,"rw");
        
        System.out.println("Gestion de clientes o login?");
        Scanner le = new Scanner(System.in);
        String respuesta1 = le.nextLine();
        
        if(respuesta1.equals("login")){
            login();
        }
        if(respuesta1.equals("gestion de cliente")||respuesta1.equals("gestion")||respuesta1.equals("Gestion")||respuesta1.equals("gestion de clientes")){ 
            
                                //Primero hay que preguntar si queremos leer o escribir
                                System.out.println("Desea inscribir o borrar a algun cliente");
                                String respuesta = le.nextLine();
                                if(respuesta.equals("borrar")){
                                    borrarCliente();
                                }
                                if(respuesta.equals("inscribir")){
                                    inscribirCliente();
                                }
                            }
        else{
            System.err.println("No te entiendo,");
            clientes();
        }
    
}
    
    public static void leerDatos(RandomAccessFile aa, File fal) throws FileNotFoundException, IOException{
        int longitud = (int)fal.length();
        int lreg = 226;
        int regs = longitud / lreg;
        System.out.println("Número total de registros en el fichero: "+regs);
        
        if (regs > 0){
            Registro vector[] = leerRegistro(aa, regs, lreg);
            for(int i=0; i<regs;i++){
                String nom = new String(vector[i].nombre);
                String dniS = new String(vector[i].dni);
                String emailS = new String(vector[i].email);
                String telefonoS = new String(vector[i].telefono);
                String pass = new String(vector[i].password);
                
                System.out.println("DNI: " + dniS+ ", Nombre:  "+nom+", email: "+ emailS+", telefono: "+telefonoS);
                }
       }else{
           System.out.println("Archivo vacio !!!");
       }
    }
    public static Registro[] leerRegistro(RandomAccessFile aa, int regs, int lreg)throws IOException{
        Registro vector[] = new Registro[regs]; 
        aa.seek(0);
        for (int i=0; i < regs; i++){
            char dni[] = new char [9];
            char nombre[] = new char[30];
            char email[] = new char[45];
            char telefono[] = new char[9];
            char password[] = new char[20];
            
            for (int k=0; k < 9; k++){
                dni[k] = aa.readChar(); 
            }
            for (int k=0; k < 30; k++){
                nombre[k] = aa.readChar(); 
            }
            for (int k=0; k < 45; k++){
                email[k] = aa.readChar(); 
            }
            for (int k=0; k < 9; k++){
                telefono[k] = aa.readChar(); 
            }
            for (int k=0; k < 20; k++){
                password[k] = aa.readChar(); 
            }
            String dniS = String.valueOf(dni);
            String nombreS = String.valueOf(nombre);
            String emailS = String.valueOf(email);
            String telefonoS = String.valueOf(telefono);
            String passwordS = String.valueOf(password);
            
            Registro datos = new Registro(dni, nombre, email, telefono, password);
            
            vector[i] = datos;
        }
        return vector;
    }
    
    public static void borrarCliente() throws FileNotFoundException, IOException{
        File fichero = new File ("clientes.txt");
        RandomAccessFile aa = new RandomAccessFile (fichero,"rw");
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Conoce el dni del cliente? si/no");
        String sino = sc.nextLine();
        if(sino.equals("si")){
            System.out.println("Introduzca el dni: ");
            String borrarDni = sc.nextLine();
            borrado(borrarDni);
            
        }if(sino.equals("no")){
            System.out.println("Estos son los registros, anote el DNI para su posterior borrado.");
            leerDatos(aa, fichero);
            borrarCliente();
        }else{
            borrarCliente();
        }
    }
    
    public static void borrado(String borrarDni) throws FileNotFoundException, IOException{
        File fichero = new File ("clientes.txt");
        RandomAccessFile aa = new RandomAccessFile (fichero,"rw");        
        
        boolean borrar = false;
        int pos = 0;
        int longitud = (int)fichero.length();//lo que ocupa el fichero
        int lreg = 226;//lo que ocupa el registro
        int regs = longitud / lreg;//numero de registros de cada fichero
        Registro vector2[] = new Registro[regs-1];
        
        if (regs > 0){
            Registro vector[] = leerRegistro(aa, regs, lreg);
            for(int i=0; i<regs;i++){
                String dniS = new String(vector[i].dni);
                if(dniS.equals(borrarDni)){
                    pos = i;
                    System.out.println("Este es el DNI que quiero borrar: " + dniS + " y esta su posicion en el vector: " + pos);
                    borrar = true;
                    break;
                }
            }
            if(!borrar){
                System.out.println("No existe, ");
            }
               
       }
        
        if(borrar){
            
            aa.seek(0);
            int contador = 0;

            for(int j = 0; j < regs ; j++){
                if(j!=pos){
                    char dni[] = new char[9];
                    char nombre[] = new char[30];
                    char email[] = new char[45];
                    char telefono[] = new char[9];
                    char password[] = new char[20];

                    for(int d = 0; d<9;d++){
                        dni[d] = aa.readChar();
                    }
                    for(int n = 0; n<30;n++){
                        nombre[n] = aa.readChar();
                    }
                    for(int e = 0; e<45;e++){
                        email[e] = aa.readChar();
                    }
                    for(int t = 0; t<9;t++){
                        telefono[t] = aa.readChar();
                    }
                    for(int p = 0; p<20;p++){
                        password[p] = aa.readChar();
                    }
                    
                    Registro reg = new Registro(dni, nombre, email, telefono, password);
                    vector2[contador] = reg;
                    contador++;
                    
                }else{
                    aa.seek(aa.getFilePointer()+226);
                }   
                 
           }
            borrarfichero();
            inscriboNuevoFichero(vector2);
            nuevaConsulta();
        }
    }
    
    public static void inscribirCliente() throws FileNotFoundException, IOException{
        
        //Crear un método que me diga si la longitud es correcta
        
        //nos creamos objeto file tipo fichero
        File fichero = new File ("clientes.txt");
        RandomAccessFile aa = new RandomAccessFile (fichero,"rw");
        //una vez creado vamos a introducir algun dato para comprobar y leerlo
        //Registro total 226 bytes/2 = 113 caracteres
        char dni[] = new char[9];
        char nombre[] = new char[30];
        char email[] = new char[45];
        char telefono[] = new char[9];
        char password[] = new char[20];
        Scanner sc = new Scanner(System.in);
        //////////////////////////////////////DNI/////////////////////////////
        System.out.println("Introduce DNI: ");
        String dniS = sc.nextLine();
        
                    boolean comprueba = false;
                    int longitud = (int)fichero.length();//lo que ocupa el fichero
                    int lreg = 226;//lo que ocupa el registro
                    int regs = longitud / lreg;//numero de registros de cada fichero


                    if (regs > 0){
                        Registro vector[] = leerRegistro(aa, regs, lreg);
                        for(int i=0; i<regs;i++){
                            String dniV = new String(vector[i].dni);
                            if(dniS.equals(dniV)){
                                comprueba = true;
                                System.out.println("Este DNI ya existe en la base de datos, por favor vuelva a intentarlo"); 
                            }
                        }            
                    }
        if(!comprueba){      
        
        
            for(int i = 0 ; i<9;i++){
                dni[i] = dniS.charAt(i);
            }       

            ////////////////////////////////nombre////////////////////////////////////
            System.out.println("Introduce Nombre: ");
            String nombreS = sc.nextLine();
            for(int i = 0 ; i<nombreS.length();i++){
                nombre[i] = nombreS.charAt(i);
            }

            //////////////////////////////////email///////////////////////////////////
            System.out.println("Introduce email: ");
            String emailS = sc.nextLine();
            for(int i = 0 ; i<emailS.length();i++){
                email[i] = emailS.charAt(i);
            }

            ////////////////////////////////telefono/////////////////////////////////
            System.out.println("Introduce teléfono: ");
            String telefonoS = sc.nextLine();
            for(int i = 0 ; i<9;i++){
                telefono[i] = telefonoS.charAt(i);
            }

            ///////////////////////////////////password/////////////////////////////
            System.out.println("Introduce password(Maximo 20 caracteres o peta cabron!!!): ");
            String passwordS = sc.nextLine();
            for(int i = 0 ; i<passwordS.length();i++){
                password[i] = passwordS.charAt(i);
            }

            ////////////////////////////////////////////////////////////////////////
        }else{
            inscribirCliente();
        }
        Registro reg = new Registro(dni, nombre, email, telefono, password);
        grabarDatos(aa, reg, fichero);
        nuevaConsulta();
    }
    
    public static void login() throws FileNotFoundException, IOException{
        System.out.println("Introduce dni: ");
        Scanner le = new Scanner(System.in);
        String dni = le.nextLine();
        System.out.println("Introduce contraseña: ");
        String pass = le.nextLine();
        //Sirve para saber si existe o no el usuario con contraseña en el fichero
        boolean existe = false;
        //Comparamos pass con passwordC para ver si coinciden --> ESTRUCTURA
        char passwordC[] = new char[20];
        for(int i = 0 ; i<pass.length();i++){
            passwordC[i] = pass.charAt(i);
        }
        //Igualamos el casteo a un String para compararlo, así evitamos que haya espacios
        String passWord = new String(passwordC);        
        
        File fichero = new File ("clientes.txt");
        RandomAccessFile aa = new RandomAccessFile (fichero,"rw");
        
        int pos = 0;
        int longitud = (int)fichero.length();//lo que ocupa el fichero
        int lreg = 226;//lo que ocupa el registro-->113 char
        int regs = longitud / lreg;//numero de registros de cada fichero
        String nombre="";
        
        if (regs > 0){
            Registro vector[] = leerRegistro(aa, regs, lreg);
            for(int i=0; i<regs;i++){
                String dniS = new String(vector[i].dni);
                String password = new String(vector[i].password);
                if(dniS.equals(dni)){
                    pos = i;
                    if(password.equals(passWord)){
                       existe = true;
                       nombre = new String(vector[i].nombre);
                  } 
                }
            }            
        }
        if(existe){
            pantallaInicio(nombre);
        }else{
            System.err.println("Usuario y/o contraseña incorrecto");
            clientes();
        }
        
    }
    
    public static void nuevaConsulta(){
        System.out.println("¿Desea realizar más operaciones?");
         try{
            Scanner sc = new Scanner(System.in);
            String respuesta = sc.nextLine();
            
            if(respuesta.equals("si")||respuesta.equals("yes")){
                clientes();
            }
            if(respuesta.equals("no")){
                System.out.println("Hasta pronto!");
            }
        }
        catch(Exception e){
            System.out.println("FUNCIÓN NO ENCONTRADA");
        }
    }
    
    public static void productos(){
        System.out.println("productos");
    }
    
    public static void ventas(){
        System.out.println("ventas");
    }

    private static void inscriboNuevoFichero(Registro[] vector2) throws FileNotFoundException, IOException{
        File fichero = new File ("clientes.txt");
        RandomAccessFile aa = new RandomAccessFile (fichero,"rw"); 
        
        aa.seek(0);
        
        for(int j = 0; j < vector2.length; j++){
        
            for(int i=0; i < 9;i++){
                aa.writeChar(vector2[j].dni[i]);
            }
            for(int i=0; i < 30;i++){
                aa.writeChar(vector2[j].nombre[i]);
            }
            for(int i=0; i < 45;i++){
                aa.writeChar(vector2[j].email[i]);
            }
            for(int i=0; i < 9;i++){
                aa.writeChar(vector2[j].telefono[i]);
            }
            for(int i=0; i < 20;i++){
                aa.writeChar(vector2[j].password[i]);
            }
        }
    }
    
    public static class Registro{
        char dni[], nombre[], email[], telefono[], password[];
        
        public Registro (char[] dni, char[] nombre, char[] email, char[] telefono, char[] password ){
            this.dni = new char[9];
            this.dni = dni;
            
            this.nombre = new char[30];
            this.nombre = nombre;
            
            this.email = new char[45];
            this.email = email;
            
            this.telefono = new char[9];
            this.telefono = telefono;
            
            this.password = new char[20];
            this.password = password;
        }
        
    }
    
    public static void pantallaInicio(String nombre){
        System.out.println("Bienvenid@ " + nombre);
        nuevaConsulta();
    }
    
    public static void grabarDatos(RandomAccessFile aa, Registro cliente, File fichero)throws IOException{
        int longitud = (int)fichero.length();
        aa.seek(longitud);
        
        for(int i=0; i < 9;i++){
            aa.writeChar(cliente.dni[i]);
        }
        for(int i=0; i < 30;i++){
            aa.writeChar(cliente.nombre[i]);
        }
        for(int i=0; i < 45;i++){
            aa.writeChar(cliente.email[i]);
        }
        for(int i=0; i < 9;i++){
            aa.writeChar(cliente.telefono[i]);
        }
        for(int i=0; i < 20;i++){
            aa.writeChar(cliente.password[i]);
        }
    }
    
    private static void borrarfichero() throws FileNotFoundException, IOException{
        
        FileWriter fichero = new FileWriter ("clientes.txt", false);
        BufferedWriter aa = new BufferedWriter(fichero);
        
        aa.write("");
        aa.close();
        
    }
        
}
    

