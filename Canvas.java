/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.tic.referentiel;

import cg.tic.refapp.Base;
import cg.tic.refapp.TicException;

/**
 *
 * @author chr
 */
public class Canvas {
    
    // On construit la div des canvas sous la forme suivante pour avoir tj les éléments div_proche, div_pos_mouse et le btcopy
    
    public static String div_construire(TicContexte pTicContexte, String pCanvasId, String pFctContenuGet4copy, String pHandlerOnmousemove, int pLargeur, int pHauteur, String pTitre
            , Domnode.StyleV[] pDivStyleVs, Domnode.StyleV[] pDivprocheStyleVs) throws TicException {
        String chaine;
        String jsfct_get_clip;
        String chaine_clipboard;
        Clipboard clipboard;
        Domnode.Styles div_styles = new Domnode.Styles(pDivStyleVs);
        Domnode.Styles div_proche_styles = new Domnode.Styles(pDivprocheStyleVs);
        
        Js.classejs_declarer(pTicContexte);

        jsfct_get_clip = Js.get_classejs_nom() + pCanvasId + "_" + "get_dbtitre_clip";

        pTicContexte.pageheader_script_ajouter(
                "let " + jsfct_get_clip + " = function() {\n" +
                "    let domnodes;\n" +
                "\n" +
                "    domnodes = " + Js.get_domnodes("\"" + pCanvasId + "\"") + ";\n" +
                "\n" +
                "    return " + Js.nioimg_btcopy("domnodes") + ";\n" +
                "\n" +
                "}; // end " + jsfct_get_clip + "()\n" +
                "", true);
        //clipboard = new Clipboard(jsfct_get_clip, pFctContenuGet4copy + "(\"" + pCanvasId + "\")", "Cliquez ici pour copier les valeurs du diagramme");
        clipboard = new Clipboard(pFctContenuGet4copy + "('" + pCanvasId + "')", "Cliquez ici pour copier les valeurs du diagramme");
        chaine_clipboard = clipboard.construire(pTicContexte);
        
        //chaine = "<div" + div_styles.construire() + " >";
        chaine = "";
        
        if (!Base.chaine_is_vide(pTitre)) {
            chaine += "<div >" + pTicContexte.get_skin().libelletitre_construire(pTitre) + "</div>";
        }
        
        chaine += 
                    "<div class=\"" + ClasseSkin.DIVCANVAS + "\"" + div_styles.construire() + " >" +
                        "<canvas id=\"" + pCanvasId + "\" height=\"" + pHauteur + "\" width=\"" + pLargeur + "\" onmousemove=\"" + pHandlerOnmousemove + "(event);\" >Pas de support pour les canvas</canvas>" +
                        "<div" + div_proche_styles.construire() + " >div proche</div>" +
                        "<div class=\"" + Commun.Css.DIVVALIGN + "\" >" +
                            chaine_clipboard +
                            "<div style=\"display: inline-block;\" >div pos mouse</div>" +
                        "</div>" +
                    "</div>" +
                //"</div>" +
                "";
        
        return chaine;
        
    } // end div_construire()
    public static String div_construire(TicContexte pTicContexte, String pCanvasId, String pFctContenuGet4copy, String pHandlerOnmousemove, int pLargeur, int pHauteur) throws TicException {
        return div_construire(pTicContexte, pCanvasId, pFctContenuGet4copy, pHandlerOnmousemove, pLargeur, pHauteur
                , "", null, new Domnode.StyleV[]{new Domnode.StyleV(Domnode.STYLE_ZINDEX, "5"), new Domnode.StyleV(Domnode.STYLE_WIDTH, "200px")});
    }
    
    public static class Js {

        public final static CanvasJs CANVASJS = new CanvasJs(Commun.VERSION_PKG_JS + "Canvas", TicServlet.Emplacement.DEFAUT);

        public static String nioimg_btcopy(String pObj) { return CanvasJs.nioimg_btcopy.getio(pObj); }
        
        public static String get_domnodes(String pCanvasId) { return CANVASJS.get_domnodes(pCanvasId); }

        public static String niodiv_proche(String pObj) { return CanvasJs.niodiv_proche.get(pObj); };
        public static String niodiv_bottom(String pObj) { return CanvasJs.niodiv_bottom.get(pObj); }
        public static String niodiv_posmouse(String pObj) { return CanvasJs.niodiv_posmouse.get(pObj); }
        public static String niodiv_canvas(String pObj) { return CanvasJs.niodiv_canvas.get(pObj); }

        public static String DOMNODES(String pObj) { return CanvasJs.DOMNODES.getino(pObj); }

        public static String get_classejs_nom() { return CANVASJS.nom; }

        public static void classejs_declarer(TicContexte pTicContexte) throws TicException { CANVASJS.declarer(pTicContexte); }
        
    } // end class Js
    
} // end class Canvas
