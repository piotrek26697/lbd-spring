package pl.fis.endpoints;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.fis.data.SpaceFleet;
import pl.fis.data.Spaceship;
import pl.fis.logic.ShipSorter;
import pl.fis.logic.SpaceFleetHandler;
import pl.fis.logic.errors.EntityNotFound;

@Api(value = "Space-fleet endpoint. Provides funcionality to operate space fleet")
@RestController
@RequestMapping("/api/space-fleet")
public class SpaceFleetAPI
{
	@Autowired
	SpaceFleetHandler spacefleetHandler;

	@ApiOperation(value = "Retrive information about specific ship", notes = "Returns Json format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived information about specific spaceship", response = Spaceship.class),
			@ApiResponse(code = 404, message = "Spaceship not found", response = EntityNotFound.class) })
	@RequestMapping(path = "/{spaceshipName}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Spaceship> getSpaceship(@PathVariable("spaceshipName") String name)
	{
		HttpHeaders headers = new HttpHeaders();
		CacheControl cc = CacheControl.maxAge(60, TimeUnit.SECONDS);
		cc.cachePrivate();
		headers.setCacheControl(cc);
		return new ResponseEntity<>(spacefleetHandler.getSpaceship(name), headers, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrive available ships", notes = "Returns Json format")
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<SpaceFleet> getSpaceFleet()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("private, max-age=60");
		return new ResponseEntity<>(spacefleetHandler.getSpaceFleet(), headers, HttpStatus.OK);
	}

	@ApiOperation(value = "Add ship to the fleet", notes = "Accepts Json format")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully added ship"),
			@ApiResponse(code = 400, message = "Spaceship invalid", response = MethodArgumentNotValidException.class) })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addSpaceship(@RequestBody @Valid Spaceship ship)
	{
		spacefleetHandler.addSpaceship(ship);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get sorted list of spaceships", notes = "Returns Json format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added ship")})
	@RequestMapping(path = "/ships", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Spaceship> getSortedSpaceships(@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "by", required = false) String element)
	{
		List<Spaceship> shipsList = spacefleetHandler.getSpaceFleet().getSpaceFleetShips();
		shipsList = ShipSorter.sort(order, element, shipsList);
		return shipsList;
	}
}
