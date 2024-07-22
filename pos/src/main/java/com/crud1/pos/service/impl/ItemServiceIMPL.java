package com.crud1.pos.service.impl;

import com.crud1.pos.dto.paginated.PaginatedResponseItemDTO;
import com.crud1.pos.dto.request.ItemSaveRequestDTO;
import com.crud1.pos.dto.response.ItemGetResponseDTO;
import com.crud1.pos.entity.Item;
import com.crud1.pos.exception.NotFoundException;
import com.crud1.pos.repo.ItemRepo;
import com.crud1.pos.service.ItemService;
import com.crud1.pos.util.mappers.ItemMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceIMPL implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemMapper itemMapper;


    @Override
    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO) {

       Item item = modelMapper.map(itemSaveRequestDTO,Item.class);
//        Item item = itemMapper.dtoToEntity(itemSaveRequestDTO);
        if (!itemRepo.existsById(item.getItemId())){
            itemRepo.save(item);
            return item.getItemId()+" saved successfully";
        }else {
            throw  new DuplicateKeyException("Already Added");
        }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByNameAndStatus(String itemName) {
       boolean b =true;
       List<Item> items = itemRepo.findAllByItemNameEqualsAndActiveStateEquals(itemName,b);
       if(items.size()>0){
           List<ItemGetResponseDTO> itemGetResponseDTOS = modelMapper.map(items,new TypeToken<List<ItemGetResponseDTO>>(){
           }.getType());
           return itemGetResponseDTOS;
       }else {
           throw  new RuntimeException("Item is not active");
       }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByNameAndStatusByMapstruct(String itemName) {
        boolean b =true;
        List<Item> items = itemRepo.findAllByItemNameEqualsAndActiveStateEquals(itemName,b);
        if(items.size()>0){
            List<ItemGetResponseDTO> itemGetResponseDTOS = itemMapper.entityListToDtoList(items);
           return itemGetResponseDTOS;
        }else {
            throw  new RuntimeException("Item is not active");
        }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByActiveAStatus(boolean activeStatus) {

        List<Item> items = itemRepo.findAllByActiveStateEquals(activeStatus);
        if(items.size()>0){
            List<ItemGetResponseDTO> itemGetResponseDTOS = itemMapper.entityListToDtoList(items);
            return itemGetResponseDTOS;
        }else {
            throw  new NotFoundException("Item is not active");
        }
    }

    @Override
    public PaginatedResponseItemDTO getItemByActiveStatusWithPaginated(boolean activeStatus, int page, int size) {
        Page<Item> items = itemRepo.findAllByActiveStateEquals(activeStatus, PageRequest.of(page, size));
       itemRepo.countAllByActiveStateEquals(activeStatus);
        if(items.getSize()<1){
          throw new NotFoundException("No Data");
       }
        PaginatedResponseItemDTO paginatedResponseItemDTO = new PaginatedResponseItemDTO(
                itemMapper.ListDTOToPage(items) ,
                itemRepo.countAllByActiveStateEquals(activeStatus)
        );

       return paginatedResponseItemDTO;
    }
}
