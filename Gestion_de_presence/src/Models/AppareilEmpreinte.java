// TODO: Implement full attributes and methods as per README.md
package Models;

import java.time.LocalDateTime;

public class AppareilEmpreinte {
    private static AppareilEmpreinte instance;
    private boolean estConnecte;
    private LocalDateTime dernierScan;

    private AppareilEmpreinte() {
        this.estConnecte = false;
    }

    public static AppareilEmpreinte getInstance() {
        if (instance == null) {
            instance = new AppareilEmpreinte();
        }
        return instance;
    }

    public boolean connecter() {
        // Logic to connect to fingerprint device
        this.estConnecte = true;
        return true;
    }

    public void deconnecter() {
        this.estConnecte = false;
    }

    public String scannerEmpreinte() {
        if (!estConnecte) {
            return null;
        }
        // Logic to scan fingerprint
        this.dernierScan = LocalDateTime.now();
        return "empreinte_scanee_" + System.currentTimeMillis(); // Placeholder
    }

    public boolean verifierConnexion() {
        return estConnecte;
    }

    // Getters and setters
    public boolean isEstConnecte() { return estConnecte; }
    public void setEstConnecte(boolean estConnecte) { this.estConnecte = estConnecte; }
    public LocalDateTime getDernierScan() { return dernierScan; }
    public void setDernierScan(LocalDateTime dernierScan) { this.dernierScan = dernierScan; }
}