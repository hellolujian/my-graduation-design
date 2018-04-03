<style scoped>
    img {
        height: 100%;
        width: 100%
    }
    .carousel {
        width: 200px;
    }
</style>

<template>
    <Carousel 
        class="carousel"
        :height="200"
        loop
        :autoplay="true"
        trigger="hover"
        arrow="hover"
        :radius="true">
        <CarouselItem>
            <img  src="static/image/banner/banner1.jpg">
        </CarouselItem>
        <CarouselItem>
            <img  src="static/image/banner/banner2.jpg">
        </CarouselItem>
        <CarouselItem>
            <img src="static/image/banner/banner3.jpg">
        </CarouselItem>
        <CarouselItem>
            <img  src="static/image/banner/banner4.jpg">
        </CarouselItem>
        <CarouselItem>
            <img  src="static/image/banner/banner5.jpg">
        </CarouselItem>
    </Carousel>      
</template>

<script>
    export default {
    }
</script>

