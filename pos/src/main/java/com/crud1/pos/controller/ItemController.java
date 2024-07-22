package com.crud1.pos.controller;


import com.crud1.pos.dto.paginated.PaginatedResponseItemDTO;
import com.crud1.pos.dto.request.ItemSaveRequestDTO;
import com.crud1.pos.dto.response.ItemGetResponseDTO;
import com.crud1.pos.service.ItemService;
import com.crud1.pos.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {
   @Autowired
   private ItemService itemService;

//   @PostMapping(
//           path = {"/save"}
//   )
//
//   public String saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
//       String message = itemService.saveItem(itemSaveRequestDTO);
//       return "saved";
//   }


    @PostMapping(
            path = {"/save"}
    )

    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
        String message = itemService.saveItem(itemSaveRequestDTO);
//        ResponseEntity<StandardResponse> response = new ResponseEntity<StandardResponse>(
//                new StandardResponse(201,"Success",message), HttpStatus.CREATED
//        );
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,"Success",message),
                HttpStatus.CREATED
        );
    }



   @GetMapping(
           path = "/get-by-name",
           params = "name"
    )
    public List<ItemGetResponseDTO> getItemByNameAndStatus(@RequestParam (value = "name")String itemName){
       List<ItemGetResponseDTO>itemDTOS = itemService.getItemByNameAndStatus(itemName);
       return itemDTOS;
    }

    @GetMapping(
            path = "/get-by-name-with-mapstuct",
            params = "name"
    )
    public List<ItemGetResponseDTO> getItemByNameAndStatusByMapstruct(@RequestParam (value = "name")String itemName){
        List<ItemGetResponseDTO>itemDTOS = itemService.getItemByNameAndStatusByMapstruct(itemName);
        return itemDTOS;
    }

    @GetMapping(
            path = "/get-all-item-by-status",
            params = {"activeStatus","page","size"}
    )

    public ResponseEntity<StandardResponse> getItemByActiveStatus(
            @RequestParam (value = "activeStatus") boolean activeStatus,
            @RequestParam (value = "page") int page,
            @RequestParam (value = "size") @Max(20) int size
            ){

        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getItemByActiveStatusWithPaginated(activeStatus,page,size);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"Success",paginatedResponseItemDTO),
                HttpStatus.OK
        );
    }

//    @GetMapping(
//            path = "/get-all-active-items-paginated",
//            params = {"page","size","activeState"}
//    )
//    public ResponseEntity<StandardResponse> getAllActiveItemPaginated(
//          @RequestParam (value = "page") int page,
//          @RequestParam (value = "size") @Max(20) int size,
//          @RequestParam (value = "activeState") boolean activeStatus
//    ){
//
//        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getAllActiveItemPaginated(page,size,activeStatus);
//
//        return new ResponseEntity<StandardResponse>(
//                new StandardResponse(200,"Success",paginatedResponseItemDTO),
//                HttpStatus.OK
//        );
//    }
   }
