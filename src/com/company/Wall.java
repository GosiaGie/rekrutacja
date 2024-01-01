package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Wall implements Structure{

    public List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        if(blocks == null) {return Optional.empty();}

        for(Block b:blocks) {
            if(b.getColor()!=null && b.getColor().equals(color)){
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {

        List <Block> blockByMaterial = new LinkedList<>();

        if(blocks == null) {return blockByMaterial;}

        blockByMaterial = blocks.stream().filter(b -> b.getMaterial().equals(material)).collect(Collectors.toList());

        return blockByMaterial;
    }

    @Override
    public int count() {

        if(blocks == null) {return 0;}

        //licznik dla Blocków, które wchodzą w skład CompositeBlockow
        int compositeBlocksCounter = 0;

        List<Block> compositeBlocks = blocks.stream()
                .filter(block -> List.of(block.getClass().getInterfaces()).contains(CompositeBlock.class))
                .collect(Collectors.toList());

        for (Block b: compositeBlocks) {
                CompositeBlock compositeBlock = (CompositeBlock) b;
                if(compositeBlock.getBlocks()==null) {continue;};
                compositeBlocksCounter += compositeBlock.getBlocks().size();
        }

        // CompositeBlocki nie liczę jako bloki, bo skladaja się z innych bloków - zakladam, ze tylko z Blocków,
        // nie innych CompositeBlocków
        // ilość to wszystkie Blocki + Bloki składające się na CompositeBlocki - ilość CompositeBloków
        return blocks.size() + compositeBlocksCounter - compositeBlocks.size();

    }

}
