from bottle import route, run, request, view, static_file, redirect
from sqlalchemy import *
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, relationship

#engine = create_engine('sqlite:///:memory:', echo=True)
engine = create_engine('sqlite:///sqlite.db', echo=True)
Base = declarative_base()

class Coda(Base):
    __tablename__ = 'clienti'
    id = Column(Integer, primary_key=True)
    command = Column(String(30))
    parameter = Column(String(30))
    def __init__(self,command,parameter):
        self.command = command
        self.parameter = parameter

smaker = sessionmaker(bind=engine)
session = smaker()
Base.metadata.create_all(engine)

@route('/')
@view('index.tpl')
def hello():
    pippo = Coda('pippo','ahhaheha')
    pluto = Coda('pluto','ahhaheha')
    paperino = Coda('paperino','paolino')
    comandi = [ pippo, pluto, paperino ]
    page = { 'data': '21 aprile 2013 ', 
        'comandi' : comandi }
    return page

@route('/bootstrap')
@view('boottest.tpl')
def boottest():
    pippo = Coda('avanti','ahhaheha')
    pluto = Coda('indietro','ahhaheha')
    paperino = Coda('paperino','paolino')
    comandi = [ pippo, pluto, paperino ]
    page = { 'data': '21 aprile 2013 ', 
        'clienti' : clienti }
    return page

@route('/clienti')
@view('clienti.tpl')
def clienti():
    sessione = smaker()
    clienti = sessione.query(Coda).all()
    return dict(clienti=clienti)

@route('/addcliente', method='POST')
def addcliente():
    sessione = smaker()
    command=request.forms['command']
    parameter=request.forms['parameter']
    comando = Coda(command,parameter)
    sessione.add(cliente)
    sessione.commit()
    redirect('/clienti')
    #return "Ho aggiunto %s %s" % (command, parameter)

@route('/chiamami', method='POST')
def chiamami():
    print request.forms.keys()
    print request.forms['sarcazzo']
    return "Ciao da %s" % request.forms.get('sarcazzo')


@route('/gestisci/<oggetto>/<azione>')
def azionioggetto(oggetto,azione):
    return 'Hai invocato azione %s su oggetto %s' % (azione,oggetto)

@route('/file/<filepath:path>')
def server_static(filepath):
    return static_file(filepath, root='file')

run(host='localhost', port=8080, debug=True, reloader=True)
