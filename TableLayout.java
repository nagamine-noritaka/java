import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * table layout is like a HTML table element.
 * and you can use like writing HTML,
 * you add layout by table row, table data. 
 * 
 * TABLE property
 *  margin
 * TR property
 *  none
 * TD property
 *  width, height, colspan, (rowspan) ,
 *  margin, align, valign  
 *  
 * not support
 *  border, padding
 *   
 * for calculation width or height.  
 *  TD.width.
 *  TD.autocalc
 *  
 *  TD.height.
 *  TD.autocalc
 *  TR.defaultHeight.
 *  
 * for calculation by position 
 *  top: sum([0-rows](height + margin.top + margin.bottom))
 *  left: sum([0-columns](width + margin.left + margin.right))
 *  
 *  LayoutManager 
 *  addLayoutComponent 
 *  invalidateLayout
 *  layoutContainer
 *  
 *  size:layoutContainer
 *  
 * and css style sheet.
 */
public class TableLayout implements LayoutManager2 {

	/**
	 * component align type.
	 */
	public enum AlignType {
		/**left*/
		left,
		/**center*/
		center,
		/**right*/
		right,
		/**none*/
		none,
	};
	/**
	 * component vertical align type.
	 */
	public enum VAlignType {
		/**top*/
		top,
		/**middle(center)*/
		middle,
		/**bottom*/
		bottom,
		/**none*/
		none,
	};

	/**
	 * container.
	 */
	Container container = null;
	/**
	 * component list.
	 */
	ArrayList<Component> list = new ArrayList<Component>();
	/**
	 * table.
	 */
	Table layout = new Table();

	@Override
	public void layoutContainer(Container arg0) {
		container = arg0;
		layout.setSize (container.getSize());
	}
	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return null;
	}
	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return arg0.getSize();
	}
	@Override
	public void removeLayoutComponent(Component arg0) {
		list.remove(arg0);
	}
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		list.add(arg1);
	}
	@Override
	public void addLayoutComponent(Component arg0, Object arg1) {
		list.add(arg0);
	}
	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}
	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}
	@Override
	public void invalidateLayout(Container arg0) {
	}
	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		return null;
	}
	/**
	 * constructor.
	 */
	public TableLayout () {
	}
	/**
	 * constructor.
	 * @param margin
	 */
	public TableLayout (final Insets margin) {
		this.layout.margin = margin;
	}
	/**
	 * @return size of table.
	 */
	public Dimension getSize() {
		return this.layout.size;
	}
	/**
	 * add table row.
	 * @return table row.
	 */
	public TR addTR(){
		return this.layout.addTR();
	}
	/**
	 * add table row.
	 * @param height table height.
	 * @return table row.
	 */
	public TR addTR(final String height){
		return this.layout.addTR(height);
	}
	/**
	 * add table row.
	 * @param height table height.
	 * @return table row.
	 */
	public TR addTR(final int height){
		return this.layout.addTR(height);
	}
	/**
	 * get component.
	 * @param index
	 * @return
	 */
	public Component getComponent(final int index) {
		if( list.size() <= index ) {
			return null;
		}
		return list.get(index);
	}
	/**
	 * get component by table row and column. 
	 * @param row row.
	 * @param col column.
	 * @return component.
	 */
	public Component getComponentAt(final int row, final int col) {
		
		if( layout.size() <= row ) {
			return null;
		}
		TR tr = layout.get(row);
		if( tr == null ) {
			return null;
		}
		if( tr.size() <= col ) {
			return null;
		}
		TD td = tr.get(col);
		if( td == null ) {
			return null;
		}
		return td.component;
	}
	/**
	 * table.
	 */
	@SuppressWarnings("serial")
	public class Table extends ArrayList<TR> {

		/**size*/
		Dimension size = new Dimension();
		/**margin*/
		Insets margin = new Insets(0,0,0,0);
		/**table width.*/
		HashMap<Integer, Integer> width = new HashMap<Integer, Integer> ();

		/**
		 * add table row.
		 * @return Table row.
		 */
		public TR addTR(){
			TR tr = new TR(this, "");
			this.add(tr);
			return tr;
		}
		/**
		 * add table row.
		 * @param height table height.
		 * @return Table row.
		 */
		public TR addTR(final String height){
			TR tr = new TR(this, height);
			this.add(tr);
			return tr;
		}
		/**
		 * add table row.
		 * @param height table height.
		 * @return Table row.
		 */
		public TR addTR(final int height){
			TR tr = new TR(this, Integer.toString(height));
			this.add(tr);
			return tr;
		}
		/**
		 * @param margin margin(outside)
		 */
		public void margin(final Insets margin) {
			this.margin = margin;
		}
		/**
		 * @return rows count.
		 */
		public int getRowsCount() {
			return this.size();
		}
		/**
		 * @return columns count.
		 */
		public int getColumnsCount() {
			int ret = 0;
			for( TR tr : this ) {
				int col = tr.getColumnsCount();
				if( ret < col ) {
					ret = col;
				}
			}
			return ret;
		}
		/**
		 * @return width.
		 */
		private int getWidth () {
			return this.size.width - margin.left - margin.right;
		}
		/**
		 * @return height;
		 */
		private int getHeight () {
			return this.size.height - margin.top - margin.bottom;
		}
		/**
		 * width.
		 * @param columnPosition column index.
		 * @return width.
		 */
		private int getWidth (final int columnPosition) {
			if( ! width.containsKey(columnPosition) ) {
				return 0;
			}
			return width.get(columnPosition);
		}
		/**
		 * width.
		 * @param columnPosition column index.
		 * @param width width.
		 */
		private void setWidth (final int columnPosition, final int width) {
			if( this.width.containsKey(columnPosition) ) {
				this.width.remove(columnPosition);
			}
			this.width.put(columnPosition, width);
		}
		/**
		 * get top position.
		 * @param rowPosition 0 origin position.
		 * @return top position.
		 */
		private int getTop (final int rowPosition) {
			int ret = 0;
			int max = rowPosition;
			for( int row =0; row < max; row++ ) {
				TR tr = get(row);
				ret += tr.height();
			}
			return ret + margin.top;
		}
		/**
		 * get left position.
		 * @param rowPosition
		 * @param columnPosition
		 * @return
		 */
		private int getLeft (final int rowPosition, final int columnPosition) {
			//copy default value.
			if( this.getRowsCount() <= rowPosition ) {
				return margin.left;
			}
			TR tr = this.get(rowPosition);
			if( tr.getColumnsCount() <= columnPosition ) {
				return margin.left;
			}
			return tr.getLeft(columnPosition)/*include table margin*/;
		}
		/**
		 * get TR's row position.
		 * @param tr
		 * @return
		 */
		private int getRow (TR tr) {
			int ret = 0;
			for( TR ctr : this ) {
				if( ctr == tr ) {
					break;
				}
				ret++;
			}
			return ret;
		}
		/**
		 * 
		 * @param td
		 * @param columnPosition
		 */
		private void copyTo (final TD td, final int columnPosition) {
			//copy default value.
			int start = this.getRowsCount() - 1;
			for(int i=start; i>=0; i--) {
				TR r = this.get(i);
				if( r.getColumnsCount() <= columnPosition ) {
					return;
				}
				TD d = r.getTD(columnPosition);
				if( d.colspan == 1) {
					d.copyTo(td);
					return;
				}
			}
		}
		/**
		 * setting size.
		 * @param size table width and height.
		 */
		public void setSize(final Dimension size) {
			//debug.
			//System.out.println(size.toString());
			
			this.size = size;
			//calculation
			this.width.clear();
			int pos = 0;
			for(TR tr : this) {
				for(TD td : tr) {
					if( list.size() <= pos ) {
						break;
					}
					Component cmp = list.get (pos);
					td.calcSize(cmp);
					td.getBounds();
					pos++;
				}
			}
			if( this.size() > 0 ) {
				TR lastTr = this.get(this.size()-1);
				if( lastTr.isDefaultHeightEmpty() ) {
					int top = getTop(this.size()-1);
					lastTr.height = String.valueOf(getHeight() - top);
				}
			}
			//set size to component.
			pos = 0;
			for(TR tr : this) {
				for(TD td : tr) {
					if( list.size() <= pos ) {
						break;
					}
					Component cmp = list.get (pos);
					td.setBounds(cmp);
					pos++;
				}
			}
			//debug.
			//System.out.print(toString());
		}
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for(TR tr : this) {
				int row = getRow(tr);
				for(TD td : tr) {
					int col = tr.getColumnPosition(td);
					sb.append (String.format("[%d,%d]", col, row));
					sb.append (td.getBounds().toString());
					sb.append ("\n");
				}
				sb.append("\r\n");
			}
			return sb.toString();
		}
	}
	/**
	 * table row.
	 */
	@SuppressWarnings("serial")
	public class TR extends ArrayList<TD> {

		/**table reference*/
		Table table = null;
		/**height*/
		String defaultHeight = "0";
		/**calculate height*/
		String height = "0";

		/**
		 * constructor.
		 * @param owner owner table.
		 * @param height row height.
		 */
		public TR (final Table owner, final String height) {
			this.table = owner;
			this.defaultHeight = height;
			this.height = height;
		}
		/**
		 * add table data.
		 * @param width width.
		 * @return table row.
		 */
		public TD addTD(final String width) {
			TD td = addTD();
			td.strWidth = width;
			return td;
		}
		/**
		 * add table data.
		 * @param width width.
		 * @return table row.
		 */
		public TD addTD(final int width) {
			TD td = addTD();
			td.strWidth = Integer.toString(width);
			return td;
		}
		/**
		 * add table data.
		 * @return table row.
		 */
		public TD addTD() {
			TD td = new TD();
			td.tr = this;
			table.copyTo(td, this.getColumnsCount());
			td.strWidth = "0";
			this.add(td);
			return td;
		}
		/**
		 * get column count.
		 * @return count.
		 */
		public int getColumnsCount() {
			int ret = 0;
			for( TD ctd : this ) {
				ret += ctd.colspan();
			}
			return ret;
		}
 		/**
		 * create copy for repeat count.
		 * @param repeat copy size.
		 * @return table row.
		 */
		public TR repeat(final int repeat) {
			TR tr = null;
			for(int i=0; i<repeat; i++) {
				tr = this.clone4r();
				table.add(tr);
			}
			return tr;
		}
		/**
		 * @param height
		 * @return Table data.
		 */
		public TR height(final String height){
			this.height = height;
			return this;
		}
		/**
		 * @return tr's height.
		 */
		int height() {
			try {
				return Integer.parseInt(this.height);
			} catch(Exception e) {
				return 0;
			}
		}
		/**
		 * get td's column index.
		 * @param td
		 * @return
		 */
		private int getColumnPosition(TD td) {
			int ret = 0;
			for( TD ctd : this ) {
				if( ctd == td ) {
					break;
				}
				ret += ctd.colspan();
			}
			return ret;
		}
		/**
		 * get width.
		 * @param columnPosition 0 origin index.
		 * @return left position.
		 */
		private int getLeft(final int columnPosition) {
			int max = columnPosition;
			int ret = 0;
			for(int i=0; i<max; i++) {
				ret += table.getWidth(i);
			}
			return ret + table.margin.left;
		}
		/**
		 * get TD.
		 * @param columnPosition
		 * @return instance.
		 */
		private TD getTD (final int columnPosition) {
			int curcol = 0;
			for( TD td : this ) {
				curcol += td.colspan();
				if( curcol >= columnPosition ) {
					return td;
				}
			}
			return null;
		}
		/**
		 * close for repeat.
		 * not copy align.
		 * @return instance.
		 */
		public TR clone4r () {
			TR tr = new TR(this.table, this.defaultHeight);
			for(TD cp : this ) {
				TD td = new TD();
				td.tr = tr;
				cp.copyAllTo(td);
				tr.add(td);
			}
			return tr;
		}
		@Override
		public TR clone () {
			TR tr = new TR(this.table, this.defaultHeight);
			for(TD cp : this ) {
				TD td = new TD();
				td.tr = tr;
				cp.copyTo(td);
				tr.add(td);
			}
			return tr;
		}
		/**
		 * default height empty?
		 * @return
		 */
		public boolean isDefaultHeightEmpty() {
			return ( defaultHeight == null ||
					defaultHeight.isEmpty() ||
					"0".equals(defaultHeight) );
		}
	}
	/**
	 * table column.
	 */
	public class TD {

		/** table row reference.*/
		TR tr = null;
		/**width*/
		String strWidth = "0";
		/**column span*/
		int colspan = 1;
		/**row span*/
		int rowspan = 1;  
		/**outside space*/
		Insets margin = new Insets(1, 1, 1, 1);
		/**horizontal align*/
		AlignType align = AlignType.none;
		/**vertical align*/
		VAlignType valign = VAlignType.none;
		/**cell component.*/
		Component component;
		/**
		 * @return calculate width.
		 */
		private int calcWidth() {
			return calculateSize(this.strWidth, tr.table.getWidth());
		}
		/**
		 * @return calculate width with margin.
		 */
		private int calcMarginWidth() {
			return calcWidth() + margin.left + margin.right;
		}
		/**
		 * @return calculate width.
		 */
		private int calcHeight() {
			if( ! tr.isDefaultHeightEmpty() ) {
				tr.height = tr.defaultHeight;
			}
			int height = tr.height() - margin.top - margin.bottom ;
			if( height < 0 ) {
				height = 0;
			}
			return calculateSize(Integer.toString(height), tr.table.getHeight());
		}
		/**
		 * @return calculate width.
		 */
		private int calcMarginHeight () {
			return calcHeight() + margin.top + margin.bottom;
		}
		/**
		 * size calculation.
		 */
		private void calcSize(final Component component) {
			//hold my component, it's my son!
			this.component = component;
			//width.
			int col = tr.getColumnPosition(this);
			int tw = tr.table.getWidth(col);
			int dtwidth = calcWidth();
			if( dtwidth <= 0 )
			{
				//not specified.
				int totalwidth = tr.table.getWidth();
				int colcnt = tr.getColumnsCount();
				int left = tr.getLeft(col) - tr.table.margin.left/*remove table margin*/;
				int divcol = colcnt - col;
				if( divcol <= 0 ) {
					divcol = 1;
				}
				dtwidth = (totalwidth - left) / divcol;
			}
			if( tw < dtwidth && colspan == 1 ) {
				tr.table.setWidth(col, dtwidth);
			}
			//height
			int myheight = calcHeight();
			if( myheight <= 0 )
			{
				//not specified.
				int row = tr.table.getRow(tr);
				int rowcnt = tr.table.getRowsCount();
				if( rowcnt > 0 ) {
					int totalheight = tr.table.getHeight();
					int top = tr.table.getTop(row) - tr.table.margin.top/*remove table margin*/;
					int divrow = rowcnt - row;
					if( divrow <= 0 ) {
						divrow = 1;
					}
					myheight = (totalheight - top) / divrow;
					tr.height = Integer.toString (myheight);
				}
			}
			//component height.
			int height = component.getHeight();
			if( myheight < height ) {
				tr.height = Integer.toString (height);
			}
		}
		/**
		 * @return width all column have same width.
		 */
		private int width() {
			return tr.table.getWidth(tr.getColumnPosition(this));
		}
		/**
		 * @param width width
		 * @return Table data.
		 */
		public TD width(final String width) {
			this.strWidth = width;
			return this;
		}
		/**
		 * @return width.
		 */
		private int height() {
			return calcMarginHeight ();
		}
		/**
		 * @return colspan size.default is 1.
		 */
		private int colspan () {
			return this.colspan;
		}
		/**
		 * @param colspan column span.
		 * @return Table data.
		 */
		public TD colspan(final int colspan) {
			if( colspan <= 0 ) {
				this.colspan = 1;
			} else {
				this.colspan = colspan;
			}
			return this;
		}
		/**
		 * @param rowspan row span.
		 * @return Table data.
		 * @deprecated future support.
		 */
		public TD rowspan(final int rowspan) {
			if( rowspan <= 0 ) {
				this.rowspan = 1;
			} else {
				this.rowspan = rowspan;
			}
			return this;
		}
		/**
		 * @param margin margin(outside)
		 * @return Table data.
		 */
		public TD margin(final Insets margin) {
			this.margin = margin;
			return this;
		}
		/**
		 * @param align align
		 * @return Table data.
		 */
		public TD align(final AlignType align){
			this.align = align;
			return this;
		}
		/**
		 * @param valign vertical align
		 * @return Table data.
		 */
		public TD valign(final VAlignType valign) {  
			this.valign = valign;
			return this;
		}
		/**
		 * add table data.
		 * @param width width.
		 * @return table row.
		 */
		public TD addTD(final String width) {
			return tr.addTD (width);
		}
		/**
		 * add table data.
		 * @param width width.
		 * @return table row.
		 */
		public TD addTD(final int width) {
			return tr.addTD (width);
		}
		/**
		 * add table data.
		 * @return table row.
		 */
		public TD addTD() {
			return tr.addTD ();
		}
 		/**
		 * create copy for repeat count.
		 * @param repeat copy size.
		 * @return table row.
		 */
		public TR repeat(final int repeat) {
			return tr.repeat (repeat);
		}
		/**
		 * total width size.
		 */
		private int  getDataWidth() {
			int myWidth = (calcMarginWidth () - margin.left - margin.right);
			int defWidth = (width () - margin.left - margin.right);
			if( colspan() > 1 ) {
				defWidth = 0;
				int col = tr.getColumnPosition(this);
				for(int i=0; i<colspan; i++, col++) {
					defWidth += tr.table.getWidth(col);
				}
				defWidth = (defWidth - margin.left - margin.right);
			}
			int width = (myWidth <= 0 ) ? defWidth : 
					(myWidth < defWidth) ? myWidth : defWidth;
			return width;
		}
		/**
		 * total height size.
		 */
		private int  getDataHeight() {
			return (height() - margin.top - margin.bottom);
		}
		/**
		 * left position.
		 */
		private int  getDataLeft() {
			return tr.table.getLeft(tr.table.getRow(tr), tr.getColumnPosition(this)) + margin.left;
		}
		/**
		 * top position.
		 */
		private int  getDataTop() {
			return tr.table.getTop (tr.table.getRow (tr)) + margin.top;
		}
		/**
		 * location and size.
		 * @return
		 */
		private Rectangle getBounds() {
			Rectangle rect = new Rectangle();
			rect.x = getDataLeft();
			rect.y = getDataTop();
			rect.width = getDataWidth();
			rect.height = getDataHeight();
			return rect;
		}
		/**
		 * change component bound.
		 * @param component
		 */
		public void setBounds (final Component component) {
			Rectangle rect = this.getBounds();
			if( component == null ) {
				return;
			}
			Dimension r = component.getPreferredSize();
			if( 0 < r.height ) {
				switch( valign ) {
				case bottom:
					rect.y += rect.height - r.height;
					rect.height = r.height;
					break;
				case middle:
					rect.y += (rect.height - r.height) / 2;
					rect.height = r.height;
					break;
				case top:
					rect.height = r.height;
					break;
				}
			}
			if( 0 < r.width ) {
				switch( align ) {
				case center:
					rect.x += (rect.width - r.width) / 2;
					rect.width = r.width;
					break;
				case left:
					rect.width = r.width;
					break;
				case right:
					rect.x += (rect.width - r.width);
					rect.width = r.width;
					break;
				}
			}
			component.setBounds (rect);
		}
		/**
		 * value copy.
		 * but not copy tr value.
		 * @param td copy to object.
		 */
		private void copyTo(TD td) {
			td.strWidth = this.strWidth;
			td.margin = (Insets) this.margin.clone();
			//not copy.
			//td.align = this.align;
			//td.valign = this.valign;
			//td.colspan = this.colspan;
			//td.rowspan = this.rowspan;  
		}
		/**
		 * value copy.
		 * but not copy tr value.
		 * @param td copy to object.
		 */
		private void copyAllTo(TD td) {
			td.strWidth = this.strWidth;
			td.margin = (Insets) this.margin.clone();
			td.align = this.align;
			td.valign = this.valign;
			//not copy.
			//td.colspan = this.colspan;
			//td.rowspan = this.rowspan;  
		}
	}
	/**
	 * set bottom position.
	 * @param target target component.
	 * @param left left position.
	 * @param up upper component, calc relation position.
	 * @param margin margin.
	 */
	protected static void setBottomRelation (final Component target, final int left, final Component up, final int margin) {
		int x = left;
		int y = up.getY();
		int top = up.getHeight();
		target.setLocation(x, y + top + margin);
	}
	/**
	 * set left position.
	 * @param target target component.
	 * @param left left component, calc relation position.
	 * @param margin margin.
	 */
	protected static void setRightRelation(final Component target, final Component left, final int margin) {
		int x = left.getX();
		int y = left.getY();
		int width = left.getWidth();
		target.setLocation(x + width + margin, y);
	}
	/**
	 * set right position.
	 * @param target target component.
	 * @param top top position.
	 * @param totalWidth container total width.
	 * @param margin margin.
	 */
	protected static void setEastRelation(final Component target, final int top, final int totalWidth, final int margin) {
		int width = totalWidth - (target.getWidth() + margin);
		target.setLocation(top, width);
	}
	/**
	 * calculate from string size.
	 * @param length
	 * @param totalLength
	 * @return size of integer.
	 */
	protected static int calculateSize(final String size, final int totalSize) {
		int ret = 0;
		if( size.matches("^[0-9]+%") ) {
			int szper = Integer.parseInt (size.substring(0, size.length()-1));
			ret = totalSize / 100 * szper;
		} else if( size.matches("^[0-9]+$") ) {
			ret = Integer.parseInt (size);
		}
		return ret;
	}
}
