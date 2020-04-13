Java Swing Layout Manager like Html table.
{Java,Swing,TableLayout,LayoutManager}

This is swing layout manager of Java.

In Japanese.

Concept:
HTMLでは表の整形にtable句を使います。
表だけではなくtable句を使ってのレイアウトの整形は色々な場所で応用されています。
table, Th, Tr, Tdによる段組みの構成は既に多くの人によって理解されている手法と言えます。

本クラスではJavaのSingにおけるレイアウトマネージャに
このHTMLのtable句の考えを取り入れました。

メソッドはTr, Tdのようによく知られたメソッドとなっています。
また属性もメソッドとして取り入れHTMLで書式を組むように
Swing上にレイアウトを構築できるようにしました。

In English.

Table layout is like a HTML table element.
and you can appoint the layout of the Swing to make like HTML.
You make a layout by table row (TR) and table data (TD).

Usage:
import TableLayout;

TableLayout laytout = new TableLayout ();
layout.addTR ()
.addTD (10)
.addTD (20)
.repeat (5);
this.setLayout (layout);
for(int i=0;i<10;i++) {
this.add ( new JLabel (Integer.toString (i)) );
}


Method:
TableLayout
.addTR return TR
.getComponent return Component
.getComponentAt return Component

TR
.addTD return TD
.repeat return TR
.height return TR

TD
.width return TD
.colspan return TD
.rowspan return TD
.margin return TD
.align return TD
.valign return TD
.addTD return TD


This source code is Free.
Please use this source code, and change one!

I want your report if there is a bug.
I want you to modify it if there is an idea of the function addition.

