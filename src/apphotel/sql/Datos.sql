/* 
 * Autor : Sebastián Fernández López
 * Descripción : Base de datos appHotel
 * 
 */

/* Base de datos cliente */
/* Atributos -> DNI -> 
                Dirección ->
                Nombre ->
                Localidad ->
                Provincia ->
                Clave Externa Provinicia con tabla provincia
*/

    
    

/* Base de datos Procvincia 
     Atributos -> Localidad -> 
                    Procincia ->*/


create table Provincia(
    ID integer not null generated always as identity,
    Localidad varchar(20) not null,
    Nombre varchar(20) not null,
    constraint ID_Provincia_PK primary key(ID)
);
    

create table Cliente(
    DNI varchar(20) not null,
    DIRECCION varchar(50) not null,
    Nombre varchar(20) not null,
    PROVINCIA integer not null,
    constraint DNI_Cliente_PK primary key(DNI),
    constraint Prov_Cliente_FK foreign key (Provincia) references Provincia(ID)
);    

/*
 Base de datos Reserva_Hab (Reserva Habitación)
   Atributos -> DNI -> 
                Fecha de llegada ->
                Fechas de salida -> 
                Numero de Habitaciones -> 
                Tipo de habitaciones ->
                Régimen (Alojamineto y Desayno), (Media Pensión), Pensión completa) ->
                Fumador ->
                Clave extern DNI con la tabla cliente
*/

create table Reserva_Hab(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    DNI varchar (20) not null,
    Fecha_llegada date not null,
    Fecha_salida date not null,
    Num_Habitaciones smallint not null,
    Tipo_Habitaciones char(1) not null,
    Regimen char(1) not null,
    Fumador boolean,
    constraint ID_ReservaHab_PK primary key(ID),
    constraint ReservaHab_DNI_FK foreign key (DNI) references Cliente(DNI)

);

/*
Base de datos Reserva_Salon (Reserva del Salón Habana)
   Atributos -> DNI -> 
                Tipo de evento (Banquete, Jornada, Congreso) ->
                Num de personas -> 
                Tipos de cocina (Bufé (Vegetariano o no), Carta, Pedir cita con el chef, No precisa) ->
                Necesita habitacion (Si, No) ->
                Cuantas habitaciones (En caso de que necesite habitaciones) -> 
                Fecha del Evento ->
                Numero de días -> 
                Clave externa DNI con la  tabla cliente
*/

create table Reserva_Salon(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    DNI varchar (20) not null,
    Tipo_Evento char(1) not null,
    Num_Persona smallint not null,
    Tipo_Cocina char(1),
    Necesita_Habitacion boolean,
    Cuantas_Hab smallint,
    Num_Dias smallint,
    Fecha_Evento date not null,
    constraint ID_ReservaSalon_PK primary key(ID),
    constraint ReservaSalon_DNI_FK foreign key (DNI) references Cliente(DNI)
);
            
insert into Provincia (Localidad, Nombre) values ('Coin', 'Málaga');
insert into Provincia (Localidad, Nombre) values ('Mijas', 'Málaga');
insert into Provincia (Localidad, Nombre) values ('Benalmadena', 'Málaga');
insert into Provincia (Localidad, Nombre) values ('Ronda', 'Málaga');
insert into Provincia (Localidad, Nombre) values ('Marbella', 'Málaga');
insert into Provincia (Localidad, Nombre) values ('Almuñecar', 'Granada');
insert into Provincia (Localidad, Nombre) values ('Motril', 'Granada');
insert into Provincia (Localidad, Nombre) values ('Tarifa', 'Cádiz');
insert into Provincia (Localidad, Nombre) values ('Grazalema', 'Cádiz');
insert into Provincia (Localidad, Nombre) values ('Rota', 'Cádiz');
insert into Provincia (Localidad, Nombre) values ('Riotinto', 'Huleva');
insert into Provincia (Localidad, Nombre) values ('Aracena', 'Huleva');

insert into Cliente (DNI,DIRECCION,Nombre,PROVINCIA) values ('7584298714X', 'Indiana', 'Joaquin', 1);
insert into Cliente (DNI,DIRECCION,NOMBRE,PROVINCIA) values ('1487965841P', 'Malagueta', 'María', 3);

insert into Cliente (DNI,DIRECCION,Nombre,PROVINCIA) values ('26841392R', 'Alhaurin el Grande', 'Juan Victor', 1);
