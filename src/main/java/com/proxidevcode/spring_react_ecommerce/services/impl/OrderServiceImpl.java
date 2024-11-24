package com.proxidevcode.spring_react_ecommerce.services.impl;

import java.time.LocalDateTime;
// import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.proxidevcode.spring_react_ecommerce.dtos.OrderRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.OrderResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.OrderMapper;
import com.proxidevcode.spring_react_ecommerce.models.Order;
import com.proxidevcode.spring_react_ecommerce.models.OrderProduct;
import com.proxidevcode.spring_react_ecommerce.models.Product;
// import com.proxidevcode.spring_react_ecommerce.repositories.OrderProductRepository;
import com.proxidevcode.spring_react_ecommerce.repositories.OrderRepository;
import com.proxidevcode.spring_react_ecommerce.repositories.ProductRepository;
import com.proxidevcode.spring_react_ecommerce.services.OrderService;

import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service 
@Transactional @RequiredArgsConstructor @Validated @Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JavaMailSender mailSender;
    // private final OrderProductRepository orderProductRepository;


    // public OrderServiceImpl (OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
    //     this.orderRepository = orderRepository;
    //     this.orderMapper = orderMapper;
    //     this.productRepository = productRepository;
    //     this.orderProductRepository = orderProductRepository;
    // }

    @Override
    public PagedResponse<OrderResponse> getAllOrders(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "date"));
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return PagedResponse.<OrderResponse>builder()
        .content(orderPage.getContent().stream().map(orderMapper::mapToDto).toList())
        .last(orderPage.isLast())
        .first(orderPage.isFirst())
        .totalPages(orderPage.getTotalPages())
        .totalElements(orderPage.getTotalElements())
        .number(orderPage.getNumber())
        .size(orderPage.getSize())
        .build();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return null;
    }

    @Override
    public OrderResponse createOrder(OrderRequest entity) {
        if (entity == null)  throw new IllegalArgumentException("OrderRequest is null"); 
        if (entity.getOrderProducts().isEmpty()) throw new IllegalArgumentException("Orderproducts is empty");
        // Order order = orderMapper.mapToEntity(entity);
        Set<OrderProduct> orderProducts = entity.getOrderProducts().stream().map(
            orderProductRequest -> {
                Product product = productRepository.findById(orderProductRequest.getProductId()).orElseThrow(() -> new EntityNotFoundException ("Product not found: "+ orderProductRequest.getProductId() ));
                if (product.getQuantity() < orderProductRequest.getQuantity()) {
                    throw new IllegalArgumentException("OrderProducts contains insufficient quantity");}
                product.setQuantity(product.getQuantity() - orderProductRequest.getQuantity());
                productRepository.save(product);   
                return OrderProduct.builder()
                .quantity(orderProductRequest.getQuantity())
                .price(orderProductRequest.getPrice())
                .product(product)
                .build(); 
            }
        ).collect(Collectors.toSet());
        Order order = Order.builder()
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .date(entity.getDate())
        .address(entity.getAddress())
        .orderProducts(orderProducts)
        .build();

        orderProducts.forEach(orderProduct -> orderProduct.setOrder(order));

        Order savedOrder = orderRepository.save(order);
        this.sendOrderConfirmation(savedOrder);
        return orderMapper.mapToDto(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest, String id) {
        return null;
    }

    @Override
    public OrderResponse deleteOrder(String id) {
               return null;
    }

    private void sendOrderConfirmation(Order order) {
        String toAddress = order.getEmail();
        String subject = "David "+ " Order confirmation -"+ order.getId();
        StringBuilder content = new StringBuilder("Dear "+ order.getFirstName() +",\n\n\n"
        +" Thank you for your order ! You can see detail bellow: \n\n"
        +" Order Id: "+ order.getId()+"\n"
        +" Order Date"+ LocalDateTime.now()+ "\n" 
        +" List of products:\n");
        
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            content.append("Product Name: ").append(orderProduct.getProduct().getName()).append("(Quantity: ").append(orderProduct.getQuantity()).append("),\n");
            }
            // content.append("\n\n\nBest regards,\nDavid");
            content.append("\nWe hope to see you again: \nBest regards, \nDavid shop Inc");

            try {
                MimeMessage  message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo (toAddress);
                helper.setSubject(subject);
                helper.setText(String.valueOf(content));
                mailSender.send(message);
            }catch (Exception e) {
                throw new RuntimeException ("Fail to send mail "+e);
            }
    }
    
}
