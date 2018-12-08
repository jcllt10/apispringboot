package com.indracompany.fullstack.appClinicaBackend.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.indracompany.fullstack.appClinicaBackend.dao.IConsultaDAO;
import com.indracompany.fullstack.appClinicaBackend.dao.IConsultaExamenDAO;
import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaListaExamenDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaResumenDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.FiltroConsultaDTO;
import com.indracompany.fullstack.appClinicaBackend.model.Consulta;
import com.indracompany.fullstack.appClinicaBackend.service.IConsultaService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaServiceImpl implements IConsultaService{

	@Autowired
	private IConsultaDAO dao;
	
	@Autowired
	private IConsultaExamenDAO ceDao;
	
	@Transactional//nanotacion para que realice un roolback,en caso ocurra un erro no inserte en iniguna tabla
	@Override
	public Consulta registrarTransaccional(ConsultaListaExamenDTO consultaDTO) {
		
		/*for(DetalleConsulta det : consulta.getDetalleConsulta()) {
		det.setConsulta(consulta);
	}*/
	
	consultaDTO.getConsulta().getDetalleConsulta().forEach(d -> {
		d.setConsulta(consultaDTO.getConsulta());
	});
	dao.save(consultaDTO.getConsulta());
	
	consultaDTO.getLstExamen().forEach(e -> ceDao.registrar(consultaDTO.getConsulta().getIdConsulta(), e.getIdExamen()));
	
	return consultaDTO.getConsulta();
	
	}

	@Override
	public Consulta modificar(Consulta t) {
		return dao.save(t);
	}

	@Override
	public void eliminar(int id) {
		dao.delete(id);
	}

	@Override
	public Consulta listarId(int id) {
		return dao.findOne(id);
	}

	@Override
	public List<Consulta> listar() {	
		return dao.findAll();
	}

	@Override
	public Consulta registrar(Consulta t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Consulta> buscar(FiltroConsultaDTO filtro) {
		return dao.buscar(filtro.getDni(), filtro.getNombreCompleto());
	}

	@Override
	public List<Consulta> buscarfecha(FiltroConsultaDTO filtro) {
		LocalDateTime fechaSgte = filtro.getFechaConsulta().plusDays(1);
		return dao.buscarFecha(filtro.getFechaConsulta(), fechaSgte);
	}

	@Override
	public List<ConsultaResumenDTO> listarResumen() {
		List<ConsultaResumenDTO> consulta = new ArrayList<>();

		// List<Object[]>
		// cantidad fecha
		// [1 , 27/11/2018]
		// [2 , 28/11/2018]
		dao.listarResumen().forEach(x -> {
			ConsultaResumenDTO cr = new ConsultaResumenDTO();
			cr.setCantidad(Integer.parseInt(String.valueOf(x[0])));
			cr.setFecha(String.valueOf(x[1]));
			consulta.add(cr);
		});
		return consulta;
	}
	
	//generamos un arreglo de bytes y los enviamos al cliente para que este pueda tener acceso al reporte
	@Override
	public byte[] generarReporte() {		
		byte[] data = null;
		try {
			//HashMap<String, String> params = new HashMap<String, String>();
			//params.put("txt_empresa", "MitoCode Network");
			File file = new ClassPathResource("/reports/consultas.jasper").getFile();
			//llenamos el reporte
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), null, new JRBeanCollectionDataSource(this.listarResumen()));
			//exportamos a pdf y devolvera un arreglo de bytes
			data = JasperExportManager.exportReportToPdf(print);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;	
	}

}
