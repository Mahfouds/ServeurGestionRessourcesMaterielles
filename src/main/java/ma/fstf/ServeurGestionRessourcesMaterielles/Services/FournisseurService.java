package ma.fstf.ServeurGestionRessourcesMaterielles.Services;

import ma.fstf.ServeurGestionRessourcesMaterielles.DTO.*;
import ma.fstf.ServeurGestionRessourcesMaterielles.Models.*;
import ma.fstf.ServeurGestionRessourcesMaterielles.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FournisseurService {

    @Autowired
    FournisseurRepository fouRep;
    @Autowired
    PropositionRepository PropRep;
    @Autowired
    Materiel_PropositionReposetory Mat_PropRep;
    @Autowired
    MatereilRepository MatRepo;
    @Autowired
    OrdinateurRepository OrdiRep;
    @Autowired
    ImprimanteRepository ImpRep;
    @Autowired
    Appel_OffreRep AppRep;
    @Autowired
    EnseignantRepo EnseRep;

    //Inscription
    public Fournisseur Inscrip(Fournisseur NvFour){

        fouRep.save(NvFour);
        FournisseurCnx fcnx=new FournisseurCnx();
        fcnx.setNom_societe(NvFour.getNom_societe());
        fcnx.setPass(NvFour.getPass());
        return Login(fcnx);
    }
    //CONNECTION
    public Fournisseur Login(FournisseurCnx fourLog) {
        List<Fournisseur> fournisseurList = fouRep.findAll();
        for (Fournisseur f : fournisseurList) {
            if (f.getPass().equals(fourLog.getPass()) && f.getNom_societe().equals(fourLog.getNom_societe()))
                return f;
        }
        return null;
    }

    //*****************PROPOSITION**************************

    //ADD PROPOSITION && UPDATE PROPOSITION
    public String ADDPROPO(Proposition prop){
        return ""+PropRep.save(prop).getId();

    }
    //DELETE PROPOSITION
    public boolean DELETEProp(Integer propid){

        PropRep.deleteById(propid);
        return true;

    }
    // ALL PROPOSITION
    public List<Proposition> ListeProposion(Integer id){
        Fournisseur fou=fouRep.findByid(id);
        List<Proposition> ListeProp =PropRep.findPropositionByFournisseur(fou);
        return ListeProp;

    }

    //*****************Materiel**************************


    //LISTE ORDINATEUR
    public List<OrdinateurDto> getOrdinateursDemande(Integer id)  {
        AppelOffre app=AppRep.getById(id);
        List<OrdinateurDto> list = new ArrayList<>();
        List<Materiel> malist =MatRepo.findMaterielByAppelOffre(app);
        for (int i =0;i<malist.size();i++){
            Materiel mat = malist.get(i);
            Ordinateur ordinateur= OrdiRep.findOrdinateurById(mat.getId());
            if (ordinateur!=null){
                OrdinateurDto ordinateurDto = OrdinateurDto.builder()
                        .id(mat.getId())
                        .cpu(ordinateur.getCpu())
                        .ram(ordinateur.getRam())
                        .ecran(ordinateur.getEcran())
                        .disque(ordinateur.getDisque())
                        .prix(0)
                        .marque("")
                        .build();

                list.add(ordinateurDto);}
        }
        return  list;
    }
    public List<OrdinateurDto> getOrdinateursProp(Integer id)  {
        Proposition app=PropRep.getById(id);
        List<OrdinateurDto> list = new ArrayList<>();
        List<Materiel_Proposition> malist =Mat_PropRep.findByProposition(app);
        for (int i =0;i<malist.size();i++){
            Materiel_Proposition mat = malist.get(i);
            Ordinateur ordinateur= OrdiRep.findOrdinateurById(mat.getMateriel().getId());
            if (ordinateur!=null){
                OrdinateurDto ordinateurDto = OrdinateurDto.builder()
                        .id(mat.getId())
                        .cpu(ordinateur.getCpu())
                        .ram(ordinateur.getRam())
                        .ecran(ordinateur.getEcran())
                        .disque(ordinateur.getDisque())
                        .prix(mat.getPrix())
                        .marque(mat.getMarque())
                        .id_Prop(mat.getProposition().getId())
                        .build();

                list.add(ordinateurDto);}
        }
        return  list;
    }


    //LISTE IMPRIMRNTE
    public List<ImprimenteDto> getImprimentesDemande(int id) {
        AppelOffre app=AppRep.getById(id);
        List<ImprimenteDto> list = new ArrayList<>();
        List<Materiel> malist =MatRepo.findMaterielByAppelOffre(app);
        for (int i =0;i<malist.size();i++){
            Materiel mat = malist.get(i);
            Imprimente imprimente= ImpRep.findImprimenteById(mat.getId());
            if (imprimente!=null){
                ImprimenteDto imprimanteDto = ImprimenteDto.builder()
                        .id(mat.getId())
                        .resolution(imprimente.getResolution())
                        .vitesse(imprimente.getVitesse())
                        .prix(0)
                        .marque("")
                        .build();
                list.add(imprimanteDto);}
        }
        return  list;
    }

    public List<ImprimenteDto> getImprimenteProp(Integer id)  {
        Proposition app=PropRep.getById(id);
        List<ImprimenteDto> list = new ArrayList<>();
        List<Materiel_Proposition> malist =Mat_PropRep.findByProposition(app);
        for (int i =0;i<malist.size();i++){
            Materiel_Proposition mat = malist.get(i);
            Imprimente imp= ImpRep.findImprimenteById(mat.getMateriel().getId());
            if (imp!=null){
                ImprimenteDto imprimanteDto = ImprimenteDto.builder()
                        .id(mat.getId())
                        .resolution(imp.getResolution())
                        .vitesse(imp.getVitesse())
                        .prix(mat.getPrix())
                        .marque(mat.getMarque())
                        .id_Prop(mat.getProposition().getId())
                        .build();
                list.add(imprimanteDto);}
        }
        return  list;
    }




    //ADD  ORDINATEUR
    public String ADD_Ordi(OrdinateurDto ordi){

        Materiel_Proposition MatProp=new Materiel_Proposition();
      MatProp.setMarque(ordi.getMarque());MatProp.setPrix(ordi.getPrix());
      MatProp.setProposition(PropRep.getById(ordi.getId_Prop()));
      MatProp.setMateriel(MatRepo.findMaterielById(ordi.getId()));

      return ""+Mat_PropRep.save(MatProp);

    }



    public String UPDATE_Ordi(OrdinateurDto ordi){

        Materiel_Proposition MatProp=new Materiel_Proposition();
        List<Materiel_Proposition> ListProp=Mat_PropRep.findByProposition(PropRep.getById(ordi.getId_Prop()));
        for(Materiel_Proposition Mat:ListProp){
            if(Mat.getId()== ordi.getId()){
                MatProp=Mat;
            }
        }
        MatProp.setMarque(ordi.getMarque());
        MatProp.setPrix(ordi.getPrix());

        return ""+Mat_PropRep.save(MatProp);

    }



    //ADD && UPDATE IMPRIMENTE
    public String ADD_IMPRI(ImprimenteDto Imp){

        Materiel_Proposition MatProp=new Materiel_Proposition();
        MatProp.setMarque(Imp.getMarque());MatProp.setPrix(Imp.getPrix());
        MatProp.setProposition(PropRep.getById(Imp.getId_Prop()));
        MatProp.setMateriel(MatRepo.findMaterielById(Imp.getId()));

        return ""+Mat_PropRep.save(MatProp);

    }

    //UPDATE IMPRIMENTE

    public String UPDATE_IMPRI(ImprimenteDto Imp){

        Materiel_Proposition MatProp=new Materiel_Proposition();
        List<Materiel_Proposition> ListProp=Mat_PropRep.findByProposition(PropRep.getById(Imp.getId_Prop()));
        for(Materiel_Proposition Mat:ListProp){
            if(Mat.getId()== Imp.getId()){
                MatProp=Mat;
            }
        }
        MatProp.setMarque(Imp.getMarque());
        MatProp.setPrix(Imp.getPrix());

        return ""+Mat_PropRep.save(MatProp);
    }


    //DELETE
    public String DELETE_Materiel(int id){
        Mat_PropRep.deleteById(id);
        return "DONE";
    }

    public List<Materiel> ListeEns(){
        return MatRepo.findAll();
    }

    public List<AppelOffre> ListeApp(){
       return AppRep.findAll();
    }


}
