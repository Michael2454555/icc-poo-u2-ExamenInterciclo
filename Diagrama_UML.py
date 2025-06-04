# import matplotlib.pyplot as plt
# from PIL import Image, ImageDraw, ImageFont


# width, height = 1400, 1200
# image = Image.new("RGB", (width, height), "white")
# draw = ImageDraw.Draw(image)

# # Load larger font
# try:
#     font = ImageFont.truetype("arial.ttf", 20)
#     small_font = ImageFont.truetype("arial.ttf", 16)
# except:
#     font = ImageFont.load_default()
#     small_font = ImageFont.load_default()

# # Draw package boxes
# def draw_package(x, y, w, h, label):
#     draw.rectangle([x, y, x+w, y+h], outline="black", width=2)
#     draw.text((x+10, y+10), label, fill="black", font=font)

# # src/app
# draw_package(350, 20, 500, 200, "src/app")
# draw.rectangle([370, 90, 830, 180], outline="black", width=2)
# draw.text((380, 110), "App", fill="black", font=small_font)
# draw.text((380, 140), "+ main(String[] args)", fill="black", font=small_font)

# # src/control
# ctrl_x, ctrl_y, ctrl_w, ctrl_h = 20, 220, 600, 310
# draw_package(ctrl_x, ctrl_y, ctrl_w, ctrl_h, "src/control")
# # inner rectangle
# draw.rectangle([ctrl_x+20, ctrl_y+20, ctrl_x+580, ctrl_y+290], outline="black", width=2)
# draw.text((ctrl_x+30, ctrl_y+40), "Controlador", fill="black", font=small_font)

# controller_methods = [
#     "+ iniciar()", "+ mostrarMenu()", "+ leerEntero(String): int", "+ leerCadena(String): String",
#     "+ registrarProveedor()", "+ registrarProducto()", "+ registrarServicio()", "+ registrarPaquete()",
#     "+ registrarSolicitudCompra()", "+ listarProveedores()", "+ listarProductos()", "+ listarServicios()",
#     "+ listarPaquetes()", "+ listarSolicitudes()", "+ buscarProveedorPorId()", "+ buscarProductoPorNombre()",
#     "+ buscarSolicitudPorNumero()", "+ aprobarSolicitud()", "+ rechazarSolicitud()", "+ calcularTotalSolicitud()"
# ]

# # Split into two columns
# half = len(controller_methods) // 2
# col1 = controller_methods[:half]
# col2 = controller_methods[half:]

# x1 = ctrl_x + 30
# x2 = ctrl_x + 300
# y_start = ctrl_y + 60

# for idx, m in enumerate(col1):
#     draw.text((x1, y_start + idx*25), m, fill="black", font=small_font)
# for idx, m in enumerate(col2):
#     draw.text((x2, y_start + idx*25), m, fill="black", font=small_font)

# # src/modelo
# draw_package(20, 550, 1360, 550, "src/modelo")

# # Function to draw class box
# def draw_class(x, y, title, lines):
#     w = 300
#     h = 40 + 25 * len(lines)
#     draw.rectangle([x, y, x+w, y+h], outline="black", width=2)
#     draw.text((x+10, y+10), title, fill="black", font=small_font)
#     for idx, line in enumerate(lines):
#         draw.text((x+10, y+40 + idx*25), line, fill="black", font=small_font)
#     return (x, y, x+w, y+h)

# # Draw model classes
# draw_class(40, 600, "Calculable (interface)", ["+ calcularCostoTotal()"])
# draw_class(350, 600, "Documento (abstract)", ["- numero: String", "- fecha: LocalDate", "+ mostrarResumen()"])
# draw_class(700, 600, "EstadoSolicitud (enum)", ["SOLICITADA", "EN_REVISION", "APROBADA", "RECHAZADA"])
# draw_class(40, 740, "Proveedor", ["- id: String", "- nombre: String", "+ agregarProducto(Producto)"])
# draw_class(350, 740, "Producto implements Calculable", ["- id: String", "- nombre: String", "- precioUnitario: double", "- proveedor: Proveedor", "+ calcularCostoTotal()"])
# draw_class(700, 740, "Servicio implements Calculable", ["- descripcion: String", "- tarifa: double", "+ calcularCostoTotal()"])
# draw_class(1050, 740, "PaqueteCompra implements Calculable", ["- nombre: String", "- componentes: List<Calculable>", "+ calcularCostoTotal()"])
# draw_class(350, 900, "SolicitudCompra extends Documento", ["- items: List<Calculable>", "- estado: EstadoSolicitud", "+ calcularCostoTotal()", "+ mostrarResumen()", "+ aprobar()", "+ rechazar()"])

# # Draw arrows connections
# def draw_arrow(start, end):
#     draw.line([start, end], fill="black", width=2)
#     ax, ay = start
#     bx, by = end
#     import math
#     ang = math.atan2(by - ay, bx - ax)
#     size = 12
#     p1 = (bx, by)
#     p2 = (bx - size * math.cos(ang - math.pi / 6), by - size * math.sin(ang - math.pi / 6))
#     p3 = (bx - size * math.cos(ang + math.pi / 6), by - size * math.sin(ang + math.pi / 6))
#     draw.polygon([p1, p2, p3], fill="black")

# # Arrow connections
# # App -> Controlador
# draw_arrow((600, 220), (300, 260))

# # Controlador -> Modelo classes
# draw_arrow((300, 350), (500, 740))   
# draw_arrow((300, 350), (725, 740))   
# draw_arrow((300, 350), (1125, 740))  
# draw_arrow((300, 350), (500, 900))   

# # Calculable -> implementations
# draw_arrow((190, 640), (500, 740))   
# draw_arrow((190, 640), (725, 740))   
# draw_arrow((190, 640), (1125, 740))  
# draw_arrow((190, 640), (500, 900))   

# # Documento -> SolicitudCompra
# draw_arrow((500, 640), (500, 900))

# # Proveedor -> Producto
# draw_arrow((340, 780), (350, 780))

# # Save image
# image.save("uml_diagram_final_clearer.png")
# print("Diagrama guardado como uml_diagram_final_clearer.png")
