<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://tempuri.org/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://tempuri.org/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://tempuri.org/">
      <s:element name="GetCatalog">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetCatalogResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetCatalogResult" type="tns:ProductsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ProductsRequest">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="result" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="originDate" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="listProducts" type="tns:ArrayOfProduct" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfProduct">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Product" nillable="true" type="tns:Product" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Product">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="partNumber" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="descripcion" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="precio" type="s:double" />
          <s:element minOccurs="1" maxOccurs="1" name="stock" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="iva_pct" type="s:float" />
          <s:element minOccurs="0" maxOccurs="1" name="partNumber_ori" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="upc" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="codCategoria" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="categoria" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="codMarca" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="marca" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="bundle" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetStockPriceCatalog">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetStockPriceCatalogResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetStockPriceCatalogResult" type="tns:ProductsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetStockPriceCatalogTime">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="fecha" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetStockPriceCatalogTimeResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetStockPriceCatalogTimeResult" type="tns:ProductsRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetCategories">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetCategoriesResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetCategoriesResult" type="tns:CategoryRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CategoryRequest">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="result" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="listCategories" type="tns:ArrayOfCategory" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfCategory">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Category" nillable="true" type="tns:Category" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Category">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="codCategoria" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="categoria" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetBrands">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetBrandsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetBrandsResult" type="tns:BrandRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="BrandRequest">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="result" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="listBrands" type="tns:ArrayOfBrand" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfBrand">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Brand" nillable="true" type="tns:Brand" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Brand">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="codMarca" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="marca" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetErrors">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetErrorsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetErrorsResult" type="tns:ErrorRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ErrorRequest">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="result" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="listErrors" type="tns:ArrayOfError" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfError">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Error" nillable="true" type="tns:Error" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Error">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="codError" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="error" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetXMLContenido">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="cliente" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="usuario" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetXMLContenidoResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetXMLContenidoResult" type="tns:ContentRequest" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ContentRequest">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="result" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="content">
            <s:complexType mixed="true">
              <s:sequence>
                <s:any />
              </s:sequence>
            </s:complexType>
          </s:element>
        </s:sequence>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetCatalogSoapIn">
    <wsdl:part name="parameters" element="tns:GetCatalog" />
  </wsdl:message>
  <wsdl:message name="GetCatalogSoapOut">
    <wsdl:part name="parameters" element="tns:GetCatalogResponse" />
  </wsdl:message>
  <wsdl:message name="GetStockPriceCatalogSoapIn">
    <wsdl:part name="parameters" element="tns:GetStockPriceCatalog" />
  </wsdl:message>
  <wsdl:message name="GetStockPriceCatalogSoapOut">
    <wsdl:part name="parameters" element="tns:GetStockPriceCatalogResponse" />
  </wsdl:message>
  <wsdl:message name="GetStockPriceCatalogTimeSoapIn">
    <wsdl:part name="parameters" element="tns:GetStockPriceCatalogTime" />
  </wsdl:message>
  <wsdl:message name="GetStockPriceCatalogTimeSoapOut">
    <wsdl:part name="parameters" element="tns:GetStockPriceCatalogTimeResponse" />
  </wsdl:message>
  <wsdl:message name="GetCategoriesSoapIn">
    <wsdl:part name="parameters" element="tns:GetCategories" />
  </wsdl:message>
  <wsdl:message name="GetCategoriesSoapOut">
    <wsdl:part name="parameters" element="tns:GetCategoriesResponse" />
  </wsdl:message>
  <wsdl:message name="GetBrandsSoapIn">
    <wsdl:part name="parameters" element="tns:GetBrands" />
  </wsdl:message>
  <wsdl:message name="GetBrandsSoapOut">
    <wsdl:part name="parameters" element="tns:GetBrandsResponse" />
  </wsdl:message>
  <wsdl:message name="GetErrorsSoapIn">
    <wsdl:part name="parameters" element="tns:GetErrors" />
  </wsdl:message>
  <wsdl:message name="GetErrorsSoapOut">
    <wsdl:part name="parameters" element="tns:GetErrorsResponse" />
  </wsdl:message>
  <wsdl:message name="GetXMLContenidoSoapIn">
    <wsdl:part name="parameters" element="tns:GetXMLContenido" />
  </wsdl:message>
  <wsdl:message name="GetXMLContenidoSoapOut">
    <wsdl:part name="parameters" element="tns:GetXMLContenidoResponse" />
  </wsdl:message>
  <wsdl:portType name="WSMGSoap">
    <wsdl:operation name="GetCatalog">
      <wsdl:input message="tns:GetCatalogSoapIn" />
      <wsdl:output message="tns:GetCatalogSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetStockPriceCatalog">
      <wsdl:input message="tns:GetStockPriceCatalogSoapIn" />
      <wsdl:output message="tns:GetStockPriceCatalogSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetStockPriceCatalogTime">
      <wsdl:input message="tns:GetStockPriceCatalogTimeSoapIn" />
      <wsdl:output message="tns:GetStockPriceCatalogTimeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetCategories">
      <wsdl:input message="tns:GetCategoriesSoapIn" />
      <wsdl:output message="tns:GetCategoriesSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetBrands">
      <wsdl:input message="tns:GetBrandsSoapIn" />
      <wsdl:output message="tns:GetBrandsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetErrors">
      <wsdl:input message="tns:GetErrorsSoapIn" />
      <wsdl:output message="tns:GetErrorsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetXMLContenido">
      <wsdl:input message="tns:GetXMLContenidoSoapIn" />
      <wsdl:output message="tns:GetXMLContenidoSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="WSMGSoap" type="tns:WSMGSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetCatalog">
      <soap:operation soapAction="http://tempuri.org/GetCatalog" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetStockPriceCatalog">
      <soap:operation soapAction="http://tempuri.org/GetStockPriceCatalog" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetStockPriceCatalogTime">
      <soap:operation soapAction="http://tempuri.org/GetStockPriceCatalogTime" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetCategories">
      <soap:operation soapAction="http://tempuri.org/GetCategories" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetBrands">
      <soap:operation soapAction="http://tempuri.org/GetBrands" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetErrors">
      <soap:operation soapAction="http://tempuri.org/GetErrors" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetXMLContenido">
      <soap:operation soapAction="http://tempuri.org/GetXMLContenido" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="WSMGSoap12" type="tns:WSMGSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetCatalog">
      <soap12:operation soapAction="http://tempuri.org/GetCatalog" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetStockPriceCatalog">
      <soap12:operation soapAction="http://tempuri.org/GetStockPriceCatalog" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetStockPriceCatalogTime">
      <soap12:operation soapAction="http://tempuri.org/GetStockPriceCatalogTime" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetCategories">
      <soap12:operation soapAction="http://tempuri.org/GetCategories" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetBrands">
      <soap12:operation soapAction="http://tempuri.org/GetBrands" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetErrors">
      <soap12:operation soapAction="http://tempuri.org/GetErrors" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetXMLContenido">
      <soap12:operation soapAction="http://tempuri.org/GetXMLContenido" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="WSMG">
    <wsdl:port name="WSMGSoap" binding="tns:WSMGSoap">
      <soap:address location="https://ecommerce.microglobal.com.ar/WSMG/WSMG.asmx" />
    </wsdl:port>
    <wsdl:port name="WSMGSoap12" binding="tns:WSMGSoap12">
      <soap12:address location="https://ecommerce.microglobal.com.ar/WSMG/WSMG.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>