import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Almacen {
    private Stack<Caja>[] pilas; // Arreglo de pilas para almacenar las cajas
    private JTextField empresaField1;
    private JTextField codigoField1;
    private JTextArea datosField1;
    private JButton ingresarButton;
    private JButton buscarButton;
    private JButton sacarButton;
    private JButton imprimirButton;
    private JButton salirButton;
    private JPanel panel;

    // Agregar un JComboBox para seleccionar la pila
    private JComboBox<String> pilaSelector;

    public Almacen() {
        // Inicializa las pilas
        pilas = new Stack[3];
        for (int i = 0; i < 3; i++) {
            pilas[i] = new Stack<>();
        }

        JFrame frame = new JFrame("Almacén de Cajas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Utilizamos GridBagLayout

        // Agregar el JComboBox para seleccionar la pila
        pilaSelector = new JComboBox<>(new String[]{"Pila 0", "Pila 1", "Pila 2"});
        addComponent(0, 0, 2, 1, panel, new JLabel("Seleccione la Pila:"));
        addComponent(0, 1, 2, 1, panel, pilaSelector);

        JLabel codigoLabel = new JLabel("Código de Empresa:");
        codigoField1 = new JTextField(20);
        JLabel empresaLabel = new JLabel("Nombre de Empresa:");
        empresaField1 = new JTextField(20);
        ingresarButton = new JButton("Ingresar Caja");
        buscarButton = new JButton("Buscar Caja");
        sacarButton = new JButton("Sacar Caja");
        imprimirButton = new JButton("Imprimir Pilas");
        salirButton = new JButton("Salir"); // Agregado el botón "Salir"

        datosField1 = new JTextArea(10, 30); // Establece el tamaño del JTextArea
        datosField1.setLineWrap(true); // Habilita el ajuste de línea


        addComponent(0, 2, 1, 1, panel, codigoLabel);
        addComponent(1, 2, 1, 1, panel, codigoField1);
        addComponent(0, 3, 1, 1, panel, empresaLabel);
        addComponent(1, 3, 1, 1, panel, empresaField1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ingresarButton);
        buttonPanel.add(buscarButton);
        buttonPanel.add(sacarButton);
        buttonPanel.add(imprimirButton);
        buttonPanel.add(salirButton);

        addComponent(0, 4, 2, 1, panel, buttonPanel);
        addComponent(0, 5, 2, 1, panel, new JScrollPane(datosField1));


        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(datosField1), BorderLayout.CENTER);

        // Agrega ActionListeners para los botones
        agregarActionListeners();

        frame.setVisible(true);

    }

    // Código (agregarCaja, buscarCaja, sacarCaja, imprimirPilas, etc.)

    private void agregarActionListeners() {
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCaja();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCaja();
            }
        });

        sacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacarCaja();
            }
        });

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirPilas();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void agregarCaja() {
        // Lógica para agregar una caja
        String codigo = codigoField1.getText().trim(); // Obtener el código de empresa y eliminar espacios en blanco
        String empresa = empresaField1.getText().trim(); // Obtener el nombre de empresa y eliminar espacios en blanco
        String selectedPila = (String) pilaSelector.getSelectedItem(); // Obtener la pila seleccionada

        // Verificar si tanto el código como el nombre de empresa tienen datos
        if (codigo.isEmpty() || empresa.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Debe ingresar ");

            if (codigo.isEmpty()) {
                mensaje.append("el Código de Empresa");
            }

            if (codigo.isEmpty() && empresa.isEmpty()) {
                mensaje.append(" y ");
            }

            if (empresa.isEmpty()) {
                mensaje.append("el Nombre de Empresa");
            }

            mensaje.append(".");
            datosField1.append(mensaje.toString() + "\n");
            return; // Salir del método si algún campo está vacío
        }

        // Verificar si tanto el código como el nombre de empresa tienen datos
        if (!codigo.isEmpty() && !empresa.isEmpty()) {
            // Verificar que el código contenga solo números
            if (codigo.matches("\\d+")) {
                int pilaIndex = -1; // Índice de la pila seleccionada
                if (selectedPila.equals("Pila 0")) {
                    pilaIndex = 0;
                } else if (selectedPila.equals("Pila 1")) {
                    pilaIndex = 1;
                } else if (selectedPila.equals("Pila 2")) {
                    pilaIndex = 2;
                }

                // Verificar si la pila seleccionada tiene espacio disponible
                if (pilaIndex != -1 && pilas[pilaIndex].size() < 6) {
                    // Verificar que el nombre de la empresa contenga solo letras
                    if (empresa.matches("[a-zA-Z ]+")) {
                        pilas[pilaIndex].push(new Caja(codigo, empresa));
                        datosField1.append("Caja agregada a " + selectedPila + ": Código: " + codigo + ", Empresa: " + empresa + "\n");

                        // Limpia los campos de texto después de agregar la caja
                        codigoField1.setText("");
                        empresaField1.setText("");
                    } else {
                        datosField1.append("El Nombre de Empresa debe contener solo letras.\n");
                    }
                } else {
                    datosField1.append("No hay espacio en la pila seleccionada. Seleccione otra.\n");
                }
            } else {
                datosField1.append("El Código de Empresa debe contener solo números.\n");
            }
        } else {
            datosField1.append("Debe ingresar tanto el Código de Empresa como el Nombre de Empresa.\n");
        }
    }

    private void buscarCaja() {
        // Lógica para buscar una caja por código
        String codigo = codigoField1.getText().trim(); // Obtener el código de empresa y eliminar espacios en blanco

        // Verificar si el campo de código está vacío
        if (codigo.isEmpty()) {
            datosField1.append("Debe ingresar el Código de Empresa para buscar una caja.\n");
            return; // Salir del método si el campo está vacío
        }

        boolean cajaEncontrada = false;

        for (int i = 0; i < pilas.length; i++) {
            for (int j = 0; j < pilas[i].size(); j++) {
                Caja caja = pilas[i].get(j);
                if (caja.getCodigo().equals(codigo)) {
                    datosField1.append("Caja encontrada en Pila " + i + ", Posición " + j + "\n");
                    cajaEncontrada = true;
                    break;
                }
            }
        }

        if (!cajaEncontrada) {
            datosField1.append("Caja no encontrada.\n");
        }
    }


    private void sacarCaja() {

        // Lógica para sacar una caja por código
        String codigo = codigoField1.getText();

        // Verificar si el campo de código está vacío
        if (codigo.isEmpty()) {
            datosField1.append("Debe ingresar el Código de Empresa para sacar una caja.\n");
            return; // Salir del método si el campo está vacío
        }

        Stack<Caja> pilaAuxiliar = new Stack<>();
        boolean cajaEncontrada = false;

        for (int i = 0; i < pilas.length; i++) {
            while (!pilas[i].isEmpty()) {
                Caja caja = pilas[i].pop();
                if (caja.getCodigo().equals(codigo)) {
                    cajaEncontrada = true;
                    break;
                } else {
                    pilaAuxiliar.push(caja);
                }
            }

            // Devuelve las cajas a la pila original
            while (!pilaAuxiliar.isEmpty()) {
                pilas[i].push(pilaAuxiliar.pop());
            }

            if (cajaEncontrada) {
                datosField1.append("Caja retirada de Pila " + i + "\n");
                break;
            }
        }

        if (!cajaEncontrada) {
            datosField1.append("Caja no encontrada.\n");
        }
    }

    private void imprimirPilas() {

        boolean todasPilasVacias = true; // Variable para verificar si todas las pilas están vacías

        for (int i = 0; i < pilas.length; i++) {
            if (!pilas[i].isEmpty()) {
                todasPilasVacias = false; // Al menos una pila no está vacía
                break; // Salir del ciclo si se encuentra una pila no vacía
            }
        }

        if (todasPilasVacias) {
            datosField1.append("Todas las pilas están vacías.\n");
            return; // Salir del método si todas las pilas están vacías
        }

        datosField1.append("Pilas de Cajas:\n");

        for (int i = 0; i < pilas.length; i++) {
            datosField1.append("Pila " + i + ": [\n");

            Stack<Caja> pila = pilas[i];
            for (Caja caja : pila) {
                datosField1.append("  " + caja.toString() + "\n");
            }

            datosField1.append("]\n");
        }
    }

    // Método para agregar componentes con GridBagLayout
    private void addComponent(int gridx, int gridy, int gridwidth, int gridheight, Container container, Component component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.anchor = GridBagConstraints.WEST; // Alineación izquierda
        container.add(component, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Almacen();
            }
        });
    }

}
