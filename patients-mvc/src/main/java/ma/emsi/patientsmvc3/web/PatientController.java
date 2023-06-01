package ma.emsi.patientsmvc3.web;

import ma.emsi.patientsmvc3.entities.Patient;
import ma.emsi.patientsmvc3.repositories.PatientRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/patientsJson")
    @ResponseBody
    public List<Patient> listePatients(){
        return patientRepository.findAll();
    }

    @GetMapping(path="/user/index")
    public String index(Model model ,
                                    @RequestParam(name="page",defaultValue = "0") int page ,
                                    @RequestParam(name="size",defaultValue = "5")  int size ,
                                    @RequestParam(name="keyWord", defaultValue = "") String keyWord)
    {
        Page<Patient> pagePatients=patientRepository.findByNameContains(keyWord,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyWord",keyWord);
        //retourne une vue appele patients.html
        return "patients";
    }

    @GetMapping("/admin/addPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "addPatients";
    }

    @PostMapping(path="/admin/savePatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatients(Model mode,
                       @Valid Patient patient ,
                       BindingResult bindingResult ,
                       @RequestParam (defaultValue = "0") int page ,
                       @RequestParam (defaultValue = "") String keyWord){
        if(bindingResult.hasErrors())
            return "addPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyWord="+keyWord;
    }

    @GetMapping("/admin/editPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatients(Model model ,Long id ,String keyWord ,int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyWord",keyWord);
        return "editPatients";
    }

    @GetMapping("/admin/deletePatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePatients(Long id, String keyWord, int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyWord="+keyWord;
    }
}
