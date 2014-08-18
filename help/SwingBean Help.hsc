HelpScribble project file.
10
PaN-1N4PQ19R
0
0




TRUE


1
BrowseButtons()
0
FALSE

FALSE
8
10
Scribble10
SwingBean Overview




Writing



FALSE
16
{\rtf1\ansi\ansicpg1252\deff0{\fonttbl{\f0\fnil\fcharset0 Arial;}{\f1\fnil Arial;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\lang1046\b\fs32 SwingBean Overview\f1 
\par 
\par \cf0\b0\f0\fs20 O framework SwingBean possui o prop\'f3sito de automatizar o processo de constru\'e7\'e3o de interfaces Swing em Java. Ele faz isto a partir do mapeamento das propriedades de uma classe para os campos de um formul\'e1rio ou de uma tabela. A partir da\'ed \'e9 poss\'edvel recuperar e inserir uma inst\'e2ncia daquela classe no formul\'e1rio ou na tabela, sendo que os campos ser\'e3o preenchidos e recuperados de forma autom\'e1tica.
\par 
\par Algumas da funcionalidades providas pelo framework:
\par 
\par - Constru\'e7\'e3o de formul\'e1rios e tabelas com componentes avan\'e7ados.
\par - Recupera\'e7\'e3o e inser\'e7\'e3o de beans nos formul\'e1rios e nas tabelas.
\par - Valida\'e7\'e3o de dados.
\par - Estrutura MVC com Swing que prov\'ea alta produtividade.
\par - Customiza\'e7\'e3o da apar\'eancia do formul\'e1rio.
\par - Estrutura de a\'e7\'f5es encadeadas para o tratamento de eventos.
\par \cf1\b\f1\fs32 
\par }
20
Scribble20
Trabalhando com formulários




Writing



FALSE
31
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil\fcharset0 Arial;}{\f1\fnil Arial;}{\f2\fnil\fcharset0 Courier New;}}
{\colortbl ;\red0\green0\blue255;\red0\green128\blue0;\red128\green0\blue0;}
\viewkind4\uc1\pard\cf1\b\fs32 Trabalhando com formul\'e1rios\cf0\b0\f1\fs20 
\par 
\par \f0 Para criar um formul\'e1rio basta criar um JBeanPanel passando a classe para a qual o cadastro est\'e1 sendo feito. Um exemplo do c\'f3digo \'e9 mosrado abaixo:
\par 
\par \f2 JBeanPanel<Funcionario> panel = new JBeanPanel<Funcionario>(Funcionario.class);
\par \f0 
\par Neste caso ser\'e1 criado um panel com os campos do formul\'e1rio referentes \'e0s vari\'e1veis do objeto, um embaixo do outro e com o t\'edtulo igual ao t\'edtulo da classe. 
\par 
\par Para alterar o t\'edtulo deve ser utilizado um outro construtor que recebe uma String adicional como par\'e2metro. Se o desejado for que o formul\'e1rio n\'e3o tenha borda ou t\'edtulo, deve ser passado um valor nulo para este par\'e2metro.
\par \f1 
\par \f2 JBeanPanel<Funcionario> panel = new JBeanPanel<Funcionario>(Funcionario.class, "Titulo do Formulario");
\par \f1 
\par \f0 Um outro tipo de altera\'e7\'e3o que pode ser feita \'e9 a customiza\'e7\'e3o da posi\'e7\'e3o dos campos e do tipo dos campos. Para isto ser\'e1 utilizada uma classe do tipo FieldDescriptor, que felizmente pode ser constru\'edda a partir de um arquivo XML. Para construir o descritor \'e9 utilizada a classe XMLDescriptorFactory. Abaixo \'e9 mostrado um exemplo de como um JBeanPanel pode ser constru\'eddo desta forma. 
\par 
\par \f2 GenericFieldDescriptor descriptor = XMLDescriptorFactory.getFieldDescriptor(Funcionario.class, new File("FuncionarioForm.xml"));
\par JBeanPanel<Funcionario> panel = new JBeanPanel<Funcionario>(Funcionario.class, descriptor);\f0 
\par 
\par O\cf2\strike  formato do arquivo XML\cf3\strike0\{linkID=40\}\cf0  ser\'e1 descrito nos pr\'f3ximos t\'f3picos. A inser\'e7\'e3o do XML pode ser feita atrav\'e9s de um InputStream, de uma String ou de um File.
\par 
\par Para que os campos de um formul\'e1rios sejam preenchidos com as informa\'e7\'f5es de um objeto deve ser utilizado o seguinte m\'e9todo:
\par 
\par \f2 panel.setBean(funcionario);\f0 
\par 
\par E para que o objeto seja preenchido com as informa\'e7\'f5es dos campos deve ser utilizado o m\'e9todo abaixo:
\par 
\par \f2 panel.populateBean(funcionario);\f0 
\par 
\par  \f1 
\par }
30
Scribble30
Criando uma tabela




Writing



FALSE
6
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil\fcharset0 Arial;}{\f1\fnil Arial;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\b\fs32 Criando uma tabela\cf0\b0\f1\fs20 
\par 
\par 
\par }
35
Scribble35
Ordenando e Filtrando uma Tabela




Writing



FALSE
18
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil\fcharset0 Arial;}{\f1\fnil Arial;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\lang2070\b\fs32 Ordenando e Filtrando uma Tabela\cf0\lang1046\b0\f1\fs20 
\par 
\par \lang2070\f0 Abaixo s\'e3o descritas fun\'e7\'f5es da classe BeanTableModel que auxiliam na organiza\'e7\'e3o dos dados. No package org.swingBean.visualTest.tablefilter do c\'f3digo fonte existe um exemplo que mostra uma impleenta\'e7\'e3o destas funcionalidades.\lang1046\f1 
\par 
\par \cf1\lang2070\b\f0\fs24 Ordena\'e7\'e3o\cf0\lang1046\b0\f1\fs20 
\par 
\par \lang2070\f0 Os dados da tabela podem ser ordenados facilmente utilizando a fun\'e7\'e3o orderByProperty da classe BeanTableModel que recebe como par\'e2metro uma String que \'e9 a propriedade pela qual os beans devem ser ordenados. Esta propriedade deve implementar a interface Comparable para acontecer a ordena\'e7\'e3o. Depois de chamada a fun\'e7\'e3o, qualquer valor inserido ou alterado ir\'e1 causar uma reordena\'e7\'e3o dos objetos.
\par 
\par \cf1\b\fs24 Busca\cf0\lang1046\b0\f1\fs20 
\par 
\par \lang2070\f0 A classe BeanTableModel tamb\'e9m facilita a busca de valores dentro de uma tabela. Atrav\'e9s dos m\'e9todos getIndexStartedBy (que retorna o indice do primeiro objeto cuja propriedade especificada \ul come\'e7ar\ulnone  com a String) e getIndexContains (que retorna o indice do primeiro objeto cuja propriedade especificada \ul conter\ulnone  a String). No caso da propriedade n\'e3o ser do tipo String, o m\'e9todo toString ser\'e1 utilizado para a compara\'e7\'e3o.
\par 
\par \cf1\b\fs24 Filtragem\cf0\lang1046\b0\f1\fs20 
\par 
\par \lang2070\f0 Os dados da tabela tamb\'e9m podem ser filtrados utilizando os m\'e9todos filterStartedBy e filterContains da classe BeanTableModel, com funcionamento an\'e1logo ao acima. Se caso mais de uma filtragem for realizada, esta ser\'e1 feita em rela\'e7\'e3o a listagem completa. Quais indices obtidos s\'e3o em rela\'e7\'e3o a lista filtrada.\lang1046\f1 
\par }
40
Scribble40
XML de descrição 




Writing



FALSE
6
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil\fcharset0 Arial;}{\f1\fnil Arial;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\b\fs32 XML de descri\'e7\'e3o \cf0\b0\f1\fs20 
\par 
\par 
\par }
50
Scribble50
Tipos de Componente




Writing



FALSE
25
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil Arial;}{\f1\fnil\fcharset0 Arial;}{\f2\fnil\fcharset2 Symbol;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\b\f0\fs32 Tipos de Componente\cf0\b0\fs20 
\par 
\par \f1 Seguem abaixo os tipos de componentes existentes no framework:
\par 
\par \pard{\pntext\f2\'B7\tab}{\*\pn\pnlvlblt\pnf2\pnindent0{\pntxtb\'B7}}\fi-200\li200\tx200\f0 TEXT\f1  - uma caixa de texto (default para campos do tipo String)\f0 
\par {\pntext\f2\'B7\tab}COMBO\f1  - um combo box  (default se possuir um dos atributos comboModelClass ou comboList)\f0 
\par {\pntext\f2\'B7\tab}DATE\f1  - um componente de calend\'e1rio (default para campos do tipo Date)\f0 
\par {\pntext\f2\'B7\tab}INTEGER\f1  - uma caixa de texto que aceita apenas inteiros (default para campos do tipo int)\f0 
\par {\pntext\f2\'B7\tab}BOOLEAN\f1  - um checkbox (default par ao tipo boolean)\f0 
\par {\pntext\f2\'B7\tab}\pard{\pntext\f2\'B7\tab}{\*\pn\pnlvlblt\pnf2\pnindent0{\pntxtb\'B7}}\fi-200\li200\tx200 DOUBLE\f1  - uma caixa de texto que aceita apenas decimais (default para campos do tipo double)\f0 
\par {\pntext\f2\'B7\tab}FLOAT\f1  - uma caixa de texto que aceita apenas decimais (default para campos do tipo float)\f0 
\par {\pntext\f2\'B7\tab}\pard{\pntext\f2\'B7\tab}{\*\pn\pnlvlblt\pnf2\pnindent0{\pntxtb\'B7}}\fi-200\li200\tx200 LONG\f1  - uma caixa de texto que aceita apenas inteiros (default para campos do tipo long)\f0 
\par {\pntext\f2\'B7\tab}LARGE_TEXT\f1 * - uma caixa grande de texto para entrada de String. Deve ser configurada explicitamente.\f0 
\par {\pntext\f2\'B7\tab}PASSWORD\f1 * - uma caixa de texto com m\'e1scara. Deve ser configurada explicitamente.\f0 
\par {\pntext\f2\'B7\tab}DEPENDENT_COMBO\f1  - um combo box que depende de outro (default se possuir o atributo dependentProperty)\f0 
\par {\pntext\f2\'B7\tab}MULTIPLE_LIST\f1 * - duas listas com "drag and drop" para arrays de objetos (default se possuir um dos atributos listModelClass ou list)\f0 
\par {\pntext\f2\'B7\tab}CHECKBOX_LIST\f1 * - uma lista de checkboxes para arrays de objetos. Deve ser configurada explicitamente.\f0 
\par {\pntext\f2\'B7\tab}IMAGE\f1 ** - uma lista para entrada de imagens em array de bytes (default se for array de bytes)\f0 
\par {\pntext\f2\'B7\tab}COLOR\f1  - um componente de escolha de cores (default para campos do tipo Color)\f0 
\par {\pntext\f2\'B7\tab}\pard\tx200 
\par \f1 * Estes campos n\'e3o s\'e3o adequados para tabelas
\par ** Este campo na tabela deve ser configrado como readOnly e n\'e3o pode ser edit\'e1vel.\f0 
\par }
60
Scribble60
Atributos dos Campos




Writing



FALSE
6
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil Arial;}{\f1\fnil\fcharset0 Arial;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\b\f0\fs32 Atributos dos Campos\cf0\b0\fs20 
\par 
\par \f1 Existem v\'e1rios tipos de atributo que podem ser atribu\'eddos as propriedades no XML, sendo alguns mais b\'e1sicos e outros mais espec\'edficos de cada componente. Abaixo seguem os tipos de atributos divididos por categorias:\f0 
\par }
70
Scribble70
Usando Cache




Writing



FALSE
46
{\rtf1\ansi\ansicpg1252\deff0\deflang1046{\fonttbl{\f0\fnil\fcharset0 Arial;}{\f1\fnil Arial;}{\f2\fnil\fcharset0 Courier New;}}
{\colortbl ;\red0\green0\blue255;}
\viewkind4\uc1\pard\cf1\lang2070\b\fs32 Usando Cache\cf0\lang1046\b0\f1\fs20 
\par 
\par \lang2070\f0 O framework oferece tr\'eas tipos de cache: um que nunca expira, um que tem um tempo para expirar e outro que verifica em um m\'e9todo se as informa\'e7\'f5es foram modificadas
\par 
\par \cf1\b\fs24 BasicCache
\par 
\par \cf0\b0\fs20 Este tipo de cache nunca expira. Primeiramente os m\'e9todos para terem cache devem estar em uma interface. Os m\'e9todos que devem ser cacheados devem possuir a anota\'e7\'e3o @Cache e os que devem zerar o cache devem possuir a anota\'e7\'e3o @InvalidateCache. Os m\'e9todos que n\'e3o devem possuir cache e nem invalidar o cache devem ficar sem anota\'e7\'f5es.
\par 
\par Abaixo segue o exemplo de uma interface com anota\'e7\'f5es para ser configurada pelo cache. Ao chamar os dois primeiros m\'e9todos, o resultado ser\'e1 incluido no cache e em uma chamada seguinte este m\'e9todo n\'e3o ser\'e1 executado e o resultado cacheado ser\'e1 retornado. Os tr\'eas ultimos m\'e9todos, pelo fato de ao serem chamados poderem influenciar no resultado dos m\'e9todos com cache, ir\'e3o excluir os resultados do cache deixando o mesmo vazio.
\par 
\par \f2  public interface DAO\{
\par    @Cache public Object getById(String ID);
\par    @Cache public List getByParams(String[] params);
\par    @InvalidateCache public void insert(Object obj);
\par    @InvalidateCache public void update(Object obj);
\par    @InvalidateCache public void delete(String ID);
\par  \}
\par 
\par \f0 Para que o cache funcione \'e9 importante que os m\'e9todos sejam chamados na mesma inst\'e2ncia. Para isto \'e9 recomendado que o objeto implemente o padr\'e3o de projeto Singleton. No momento de criar a inst\'e2ncia do objeto, deve ser criado o proxy din\'e2mico para "instalar" o cache no objeto. Abaixo temos um exemplo de como isto funcionaria:
\par 
\par \f2  public class ImplDAO implements DAO\{
\par 
\par    private static DAO instance;
\par 
\par    public static DAO getInstance()\{
\par      if(instance == null)
\par        instance = (DAO) BasicCache.createProxy(new ImplDAO());
\par      return instance;
\par    \}
\par 
\par    //Implementa\'e7\'e3o dos m\'e9todos da interface
\par 
\par  \}
\par 
\par \cf1\b\f0\fs24 TimedCache
\par 
\par \cf0\b0\fs20 Este tipo de cache possui um tempo desde a primeira chamada para expirar e esvaziar o cache. O tempo default \'e9 de 10 min, mas pode ser passado no construtor o tempo desejado em milisegundos. Segue abaixo o exemplo para configurar o cache para exirar em 5 minutos:
\par 
\par \f2    instance = (DAO) TimedCache.createProxy(new ImplDAO(),5*60*1000);
\par 
\par \cf1\b\f0\fs24 VerifyCache
\par 
\par \cf0\b0\fs20 Este tipo de cache ir\'e1 funcionar para objetos que implementarem a interface CacheVerifyer. Esta interface possui um \'fanico m\'e9todo \f2 public long getInformationTimeMillis() \f0 que deve retornar a hora da \'faltima mudan\'e7a. Caso o valor seja igual o do \'faltimo m\'e9todo executado, ser\'e1 retornado o valor do cache. Caso o valor seja diferente, o m\'e9todo ser\'e1 executado e armazenado no cache. A cria\'e7\'e3o do objeto proxy \'e9 semelhante a mostrada para o BasicCache.\lang1046\f1 
\par }
0
0
0
0
6
*InternetLink
16711680
Courier New
0
10
1
....
0
0
0
0
0
0
*ParagraphTitle
-2147483640
Arial
0
11
1
B...
0
0
0
0
0
0
*PopupLink
-2147483640
Arial
0
8
1
....
0
0
0
0
0
0
*PopupTopicTitle
16711680
Arial
0
10
1
B...
0
0
0
0
0
0
*TopicText
-2147483640
Arial
0
10
1
....
0
0
0
0
0
0
*TopicTitle
16711680
Arial
0
16
1
B...
0
0
0
0
0
0
