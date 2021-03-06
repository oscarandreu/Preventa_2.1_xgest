/******************************************************************************
 *	Copyright (c) 2004 Actuate Corporation and others.
 *	All rights reserved. This program and the accompanying materials 
 *	are made available under the terms of the Eclipse Public License v1.0
 *	which accompanies this distribution, and is available at
 *		http://www.eclipse.org/legal/epl-v10.html
 *	
 *	Contributors:
 *		Actuate Corporation - Initial implementation.
 *****************************************************************************/
 
/**
 *	BirtNavigationBar.
 *	TODO: comments.
 */
BirtNavigationBar = Class.create( );

BirtNavigationBar.prototype = Object.extend( new AbstractBaseNavigationBar( ),
{
	/**
	 *	Initialization routine required by "ProtoType" lib.
	 *	@return, void
	 */
	initialize : function( id )
	{
		this.__initBase( id );
		this.__oPageNumber = $( 'pageNumber' );
		this.__oTotalPage = $( 'totalPage' );
		this.__cb_installEventHandlers( );
	},

	/**
	 *	Binding data to the navigation bar UI. Data includes page number, total
	 *	page number (optional).
	 *
	 *	@data, data DOM tree (Schema TBD)
	 *	@return, void
	 */
	__cb_bind : function( data )
	{
		if ( !data )
		{
			return;
		}
		
		var oPageNumbers = data.getElementsByTagName( 'PageNumber' );
		if ( !oPageNumbers && !oPageNumbers[0] )
		{
			return;
		}
		
		this.__oPageNumber.innerHTML = oPageNumbers[0].firstChild.data;
		
		var oTotalPages = data.getElementsByTagName( 'TotalPage' );
		this.__oTotalPage.innerHTML = ( oTotalPages && oTotalPages[0] )? oTotalPages[0].firstChild.data : '+';
		
		var pageNumber = parseInt( this.__oPageNumber.firstChild.data );
		var totalPage = ( this.__oTotalPage.firstChild.data == '+' )? '+' : parseInt( this.__oTotalPage.firstChild.data );

		var oImgs = this.__instance.getElementsByTagName( "INPUT" );
		oImgs[0].src = ( pageNumber > 1 ) ? "birt/images/FirstPage.gif" : "birt/images/FirstPage_disabled.gif";
		oImgs[0].style.cursor = ( pageNumber > 1 ) ? "pointer" : "default";
		oImgs[1].src = ( pageNumber > 1 ) ? "birt/images/PreviousPage.gif" : "birt/images/PreviousPage_disabled.gif";
		oImgs[1].style.cursor = ( pageNumber > 1 ) ? "pointer" : "default";
		oImgs[2].src = ( totalPage == '+' || pageNumber < totalPage ) ? "birt/images/NextPage.gif" : "birt/images/NextPage_disabled.gif";
		oImgs[2].style.cursor = ( totalPage == '+' || pageNumber < totalPage ) ? "pointer" : "default";
		oImgs[3].src = ( totalPage == '+' || pageNumber < totalPage ) ? "birt/images/LastPage.gif" : "birt/images/LastPage_disabled.gif";
		oImgs[3].style.cursor = ( totalPage == '+' || pageNumber < totalPage ) ? "pointer" : "default";
		
	},
	
	/**
	 *	Install native/birt event handlers.
	 *
	 *	@id, navigation bar id (optional since there is only one nav bar)
	 *	@return, void
	 */
	__cb_installEventHandlers : function( )
	{
		var oImgs = this.__instance.getElementsByTagName( 'INPUT' );
		
		if ( oImgs )
		{
			for ( var i = 0; i < oImgs.length; i++ )
			{
				if ( oImgs[i].type == 'image' )
				{
					Event.observe( oImgs[i], 'click', this.__neh_click.bindAsEventListener( this ), false );
				}
			}
		}
	}	
}
);