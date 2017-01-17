/*************************************************************************
                           Log  -  description
                             -------------------
    début                : $DATE$
    copyright            : (C) $YEAR$ par $AUTHOR$
    e-mail               : $EMAIL$
*************************************************************************/

//---------- Réalisation de la classe <Log> (Log Log.cpp) ------------

//---------------------------------------------------------------- INCLUDE

//-------------------------------------------------------- Include système
using namespace std;
#include <iostream>

//------------------------------------------------------ Include personnel
#include "Log.h"

//------------------------------------------------------------- Constantes
const char SEP = ' ';
//---------------------------------------------------- Variables de classe

//----------------------------------------------------------- Types privés


//----------------------------------------------------------------- PUBLIC
//-------------------------------------------------------- Fonctions amies

//----------------------------------------------------- Méthodes publiques
int Log::GetHeure()
// Algorithme :
//
{   string date = Infos.date;
    size_t pos = date.find_first_of(':');
    string res = "";
    res = date.substr(pos+1, 2);
    int heure = stoi(res);
    return heure;
} //----- Fin de GetHeure

string Log::GetType()
// Algortihme :
//
{   string c = cible;
    size_t pos = c.find_first_of('.');
    string res;
    res = c.substr(pos+1);
    return res;
} //----- Fin de GetType

//------------------------------------------------- Surcharge d'opérateurs
//Log & Log::operator = ( const Log & unLog )
// Algorithme :
//
//{
//} //----- Fin de operator =


//-------------------------------------------- Constructeurs - destructeur
Log::Log ( const Log & unLog )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au constructeur de copie de <Log>" << endl;
#endif
} //----- Fin de Log (constructeur de copie)


/*Log::Log(string c, Informations i) ( )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au constructeur de <Log>" << endl;
#endif
} //----- Fin de Log*/


Log::~Log ( )
// Algorithme :
//
{
#ifdef MAP
    cout << "Appel au destructeur de <Log>" << endl;
#endif
} //----- Fin de ~Log


//------------------------------------------------------------------ PRIVE
istream & operator >> (istream & is, Log & f)
{   getline(is, f.Infos.ip, SEP);
    getline(is, f.Infos.logname, SEP);
    getline(is, f.Infos.name, SEP);
    getline(is, f.Infos.date, SEP);
    getline(is, f.Infos.fuseau, SEP);
    getline(is, f.Infos.requete, SEP);
    getline(is, f.cible, SEP);
    getline(is, f.Infos.type, SEP);
    getline(is, f.Infos.status, SEP);
    getline(is, f.Infos.data, SEP);
    getline(is, f.Infos.referent, SEP);
    getline(is, f.Infos.navigateur);
    return is;
}

ostream & operator << (ostream & os, Log & f)
{   os << f.Infos.ip << SEP << f.Infos.logname << SEP << f.Infos.name << SEP << f.Infos.date << SEP << f.Infos.fuseau << SEP << f.Infos.requete << SEP << f.cible<< SEP << f.Infos.type << SEP << f.Infos.status;
    os << SEP << f.Infos.data << SEP << f.Infos.referent << SEP << f.Infos.navigateur << endl;
    return os;
}
//----------------------------------------------------- Méthodes protégées

//------------------------------------------------------- Méthodes privées

