/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.services;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;
import visualigue.inter.controller.VisuaLigueController;
import java.util.LinkedList;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Bruno L.L.
 */
public class Serializer {

    private final String saveFileName = "save.ser";
    private final int historyMaxSize = 100;

    private LinkedList<byte[]> history = new LinkedList<byte[]>();
    private VisuaLigueController controller;
    private int historyPointer = 0;

    public Serializer(VisuaLigueController controller) {
        this.controller = controller;
    }

    public void saveToHistory() {
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOut);
            controller.saveIdGeneration();
            out.writeObject(controller);
            out.close();

            // delete if history if too big
            int size = history.size();
            if (size > historyMaxSize - 1) {
                history.remove(size - 1);
            }
            // delete inbetween current position and this new one
            if (historyPointer > 0) {
                for (int i = historyPointer - 1; i >= 0; i--) {
                    history.remove(i);
                }
            }
            history.addFirst(byteArrayOut.toByteArray());
            historyPointer = 0;
            
            System.out.println("save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try {
            File file = new File(this.saveFileName);
            file.createNewFile();

            FileOutputStream fileOut = new FileOutputStream(this.saveFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            controller.saveIdGeneration();
            out.writeObject(controller);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        File file = new File(this.saveFileName);

        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("save.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                VisuaLigueController unserializedObj = (VisuaLigueController) in.readObject();

                this.restoreController(unserializedObj);

                in.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void undo() {
        if (historyPointer < history.size() - 1) {
            historyPointer++;
            gotoHistory(historyPointer);
        }
    }

    public void redo() {
        if (historyPointer > 0) {
            historyPointer--;
            gotoHistory(historyPointer);
        }
    }

    private void gotoHistory(int pointer) {
        try {
            ByteArrayInputStream byteArrIn = new ByteArrayInputStream(this.history.get(pointer));
            ObjectInputStream in = new ObjectInputStream(byteArrIn);
            VisuaLigueController unserializedObj = (VisuaLigueController) in.readObject();
            in.close();
            byteArrIn.close();

            this.restoreController(unserializedObj);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void restoreController(VisuaLigueController controllerToRestore) {
        controller.copy(controllerToRestore);
        controller.restoreIdGeneration();
    }
}
