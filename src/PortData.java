
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author natan
 */
public class PortData {

    private int port;
    private boolean isOpen;
    private String registro;
    private String description;

    public PortData() {

    }

    public PortData(int port, boolean isOpen, String status, String description) {
        this.port = port;
        this.isOpen = isOpen;
        this.registro = status;
        this.description = description;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean IsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof PortData) {
            return this.port == ((PortData) object).port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.port;
        return hash;
    }

    @Override
    public String toString() {
        return "PortData{" + "port=" + port + ", isOpen=" + isOpen + ", status=" + registro + ", description=" + description + '}';
    }

}
