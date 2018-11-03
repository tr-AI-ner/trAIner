package functionality;

import java.
public void createGrid(IActiveView = activeView, IPageLayout = pageLayout)throws
    IOException
{
    //Create the grid 
    IMapGrid mapGrid = new Graticule();
    mapGrid.setName("Map Grid");
    //Create a color 
    IColor color = new RgbColor();
    color.setRGB(0XBBBBBB); // -> Gray

    //Set the line symbol used to draw the grid
    ICartographicLineSymbol cartographicLineSymbol = new CartographicLineSymbol
        ();
    cartographicLineSymbol.setCap(esriLineCapStyle.esriLCSButt);
    cartographicLineSymbol.setWidth(2);
    cartographicLineSymbol.setColor(color);
    mapGrid.setLineSymbol((ILineSymbol)cartographicLineSymbol);
    mapGrid.setBorder(null); // clear the default frame border

    //Set the Tick Properties 
    mapGrid.setTickLength(15);
    cartographicLineSymbol = new CartographicLineSymbol();
    cartographicLineSymbol.setCap(esriLineCapStyle.esriLCSButt);
    cartographicLineSymbol.setWidth(1);
    cartographicLineSymbol.setColor(color);
    mapGrid.setTickLineSymbol((ILineSymbol)cartographicLineSymbol);
    mapGrid.setTickMarkSymbol(null);

    //Set the Sub Tick Properties 
    mapGrid.setSubTickCount((Short)5);
    mapGrid.setSubTickLength(10);
    cartographicLineSymbol = new CartographicLineSymbol();
    cartographicLineSymbol.setCap(esriLineCapStyle.esriLCSButt);
    cartographicLineSymbol.setWidth(0.2);
    cartographicLineSymbol.setColor(color);
    mapGrid.setSubTickLineSymbol((ILineSymbol)cartographicLineSymbol);

    // Set the Grid labels properties 
    IGridLabel gridLabel = mapGrid.getLabelFormat();
    gridLabel.setLabelOffset(15);

    //Set the Tick, SubTick, Label Visibility along the 4 sides of the grid 
    mapGrid.setTickVisibility(true, true, true, true);
    mapGrid.setSubTickVisibility(true, true, true, true);
    mapGrid.setLabelVisibility(true, true, true, true);

    //Make map grid visible, so it gets drawn when Active View is updated 
    mapGrid.setVisible(true);

    //Set the IMeasuredGrid properties 
    IMeasuredGrid measuredGrid = (IMeasuredGrid)mapGrid;
    measuredGrid.setFixedOrigin(true);
    measuredGrid.setXIntervalSize(10); //meridian interval
    measuredGrid.setXOrigin(5); //shift grid 5°
    measuredGrid.setYIntervalSize(10); //parallel interval
    measuredGrid.setYOrigin(5); //shift grid 5°

    // Add the grid to the MapFrame 
    IMap map = activeView.getFocusMap();
    IGraphicsContainer graphicsContainer = (IGraphicsContainer)pageLayout;
    IFrameElement frameElement = graphicsContainer.findFrame(map);
    IMapFrame mapFrame = (IMapFrame)frameElement;
    IMapGrids mapGrids = null;
    mapGrids = (IMapGrids)mapFrame;
    mapGrids.addMapGrid(mapGrid);

    //Refresh the view
    activeView.partialRefresh(esriViewDrawPhase.esriViewBackground, null, null);
}

