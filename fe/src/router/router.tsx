import { Auth } from "@pages/Auth";
import Chat from "@pages/Chat";
import Category from "@pages/Home/Category";
import Home from "@pages/Home/Home";
import NewProduct from "@pages/Home/NewProduct";
import MyAccount from "@pages/MyAccount/MyAccount";
import Product from "@pages/Product";
import Register from "@pages/Register";
import SalesList from "@pages/SalesList";
import WishList from "@pages/WishList";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path={ROUTE_PATH.home} element={<Home />} />
      <Route path={ROUTE_PATH.new} element={<NewProduct />} />
      <Route path={ROUTE_PATH.category} element={<Category />} />
      <Route path={ROUTE_PATH.product} element={<Product />} />
      <Route path={ROUTE_PATH.sales} element={<SalesList />} />
      <Route path={ROUTE_PATH.wish} element={<WishList />} />
      <Route path={ROUTE_PATH.chat} element={<Chat />} />
      <Route path={ROUTE_PATH.account} element={<MyAccount />} />
      <Route path={ROUTE_PATH.auth.index} element={<Auth />} />
      <Route path={ROUTE_PATH.register} element={<Register />} />
    </>
  )
);
