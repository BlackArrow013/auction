import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }
    
    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                                   lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                System.out.println("Lot number: " + lotNumber +
                                   " already has a bid of: " +
                                   selectedLot.getHighestBid().getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        Lot selectedLot = lots.get(lotNumber - 1);
        boolean salirDelBucle = false;
        while (lotNumber - 1 <= lots.size() && !salirDelBucle)
        {
            if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
                if(selectedLot.getNumber() != lotNumber) {
                    System.out.println("Internal error: Lot number " +
                                       selectedLot.getNumber() +
                                       " was returned instead of " +
                                       lotNumber);
                    // Don't return an invalid lot.
                    selectedLot = null;
                    salirDelBucle = true;
                }  
                salirDelBucle = true;
            }
            else {
                System.out.println("Lot number: " + lotNumber +
                                   " does not exist.");
                selectedLot = null;
                salirDelBucle = true;
            }
        }
        return selectedLot;
    }
    
    /**
     * Imprime detalles de todos los lotes de la colección.
     */
    public void close()
    {
        for(Lot objeto : lots) {
            System.out.println(objeto.toString());
            Bid pujaMasAlta = objeto.getHighestBid();
            if (pujaMasAlta != null) {
                System.out.println(pujaMasAlta.getBidder().getName() + " es la persona con la puja más alta con un valor de " + pujaMasAlta.getValue());
            }
            else {
                System.out.println("Aún no se ha realizado ninguna puja.");
            }
        }
    }
    
    /**
     * Devuelve todos los lotes no vendidos.
     */
    public ArrayList<Lot> getUnsold()
    {
        ArrayList<Lot> lotesNoVendidos = new ArrayList(lots);        
        for(Lot objeto : lots) {
            Bid loteNoVendido = objeto.getHighestBid();
            if (loteNoVendido != null) {
                lotesNoVendidos.remove(objeto);
            }
        }
        return lotesNoVendidos;
    }
    
    /**
     * Eliminar el lote con el número de lote especificado. 
     * @param number El número del lote que hay que eliminar.
     * @return El lote con el número dado o null si non existe tal lote.
     */
    public Lot removeLote(int number)
    {
        Lot loteRemovido = null;
        for (Lot lote : lots) {
            if (number == lote.getNumber()) {
                loteRemovido = lote;
                lots.remove(lote);
            }
        }
        return loteRemovido;
    }
}
