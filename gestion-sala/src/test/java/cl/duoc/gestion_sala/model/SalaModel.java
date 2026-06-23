package cl.duoc.gestion_sala.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaModelTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        SalaModel sala = new SalaModel();
        assertNotNull(sala);
    }

    @Test
    @DisplayName("Setters y Getters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        SalaModel sala = new SalaModel();

        sala.setId(1L);
        sala.setNombreSala("Laboratorio 402");
        sala.setCapacidad(30);
        sala.setTipo("Laboratorio");
        sala.setUbicacion("Piso 4, Edificio A");

        assertEquals(1L, sala.getId());
        assertEquals("Laboratorio 402", sala.getNombreSala());
        assertEquals(30, sala.getCapacidad());
        assertEquals("Laboratorio", sala.getTipo());
        assertEquals("Piso 4, Edificio A", sala.getUbicacion());
    }

    @Test
    @DisplayName("equals y hashCode - dos salas con los mismos datos deben ser iguales")
    void dosSalasConMismosDatosDebenSerIguales() {
        SalaModel s1 = new SalaModel();
        s1.setId(1L); s1.setNombreSala("Aula 101"); s1.setCapacidad(40);
        
        SalaModel s2 = new SalaModel();
        s2.setId(1L); s2.setNombreSala("Aula 101"); s2.setCapacidad(40);

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el nombre de la sala en la representación")
    void toStringDebeContenerNombreDeLaSala() {
        SalaModel sala = new SalaModel();
        sala.setId(3L);
        sala.setNombreSala("Auditorio San Carlos");

        String texto = sala.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Auditorio San Carlos"));
    }
}